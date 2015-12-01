package com.swami.vidyanand.utils;

import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.style.BulletSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.RelativeSizeSpan;

import org.xml.sax.XMLReader;

import java.util.Vector;

public class CustomTagHandler implements Html.TagHandler {

	private int m_index = 0;
	private Vector<String> m_parents = new Vector<String>();

	@Override
	public void handleTag(boolean opening, String tag, Editable output,
			XMLReader xmlReader) {
		if (tag.equals("ul") || tag.equals("ol") || tag.equals("dd")) {
			if (output.toString().endsWith("\n") == false) {
				output.append("\n");
			}
			if (opening) {
				m_parents.add(tag);
			} else {
				m_parents.remove(tag);
			}

			if (opening == false && tag.equals("ul")){
				output.append("\n");
			}
			m_index = 0;
		} else if (tag.equals("li") && !opening) {
			handleListTag(output);
		} else if (tag.equalsIgnoreCase("h2")) {
			processHeader(opening, output, new RelativeSizeSpan(1.2f));
		} else if (tag.equalsIgnoreCase("h3")) {
			processHeader(opening, output, new RelativeSizeSpan(0.8f));
		}
	}

	private void handleListTag(Editable output) {
		if (m_parents.lastElement().equals("ul")) {
			output.append("\n");
			String[] split = output.toString().split("\n");

			int lastIndex = split.length - 1;
			int start = output.length() - split[lastIndex].length() - 1;
			output.setSpan(new BulletSpan(15 * m_parents.size()), start,
					output.length(), 0);
		} else if (m_parents.lastElement().equals("ol")) {
			m_index++;

			output.append("\n");
			String[] split = output.toString().split("\n");

			int lastIndex = split.length - 1;
			int start = output.length() - split[lastIndex].length() - 1;
			output.insert(start, m_index + ". ");
			output.setSpan(
					new LeadingMarginSpan.Standard(15 * m_parents.size()),
					start, output.length(), 0);
		}

	}

	public void processHeader(boolean opening, Editable output, RelativeSizeSpan relSpan) {
		int len = output.length();
		if (opening) {
			output.setSpan( relSpan , len, len,
					Spannable.SPAN_MARK_MARK);
		} else {
			Object obj = getLast(output, RelativeSizeSpan.class);
			int where = output.getSpanStart(obj);
			output.removeSpan(obj);
			if (where != len) {
				output.setSpan(relSpan, where, len,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object getLast(Editable text, Class kind) {
		Object[] objs = text.getSpans(0, text.length(), kind);

		if (objs.length == 0) {
			return null;
		} else {
			for (int i = objs.length; i > 0; i--) {
				if (text.getSpanFlags(objs[i - 1]) == Spannable.SPAN_MARK_MARK) {
					return objs[i - 1];
				}
			}
			return null;
		}
	}

}
