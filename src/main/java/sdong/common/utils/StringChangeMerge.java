package sdong.common.utils;

import java.util.ArrayList;
import java.util.List;

import sdong.common.utils.StringCommonFactor.DiffSequence;

public class StringChangeMerge {

	String original;
	List<String> changeList;

	StringChangeMerge(String original, List<String> changeList) {
		this.original = original;
		this.changeList = changeList;

	}

	String generateMergeString() {
		String mergeStr = null;

		StringCommonFactor factor;
		List<DiffSequence> mergeList = new ArrayList<DiffSequence>();
		for (String changeStr : changeList) {
			factor = new StringCommonFactor(original, changeStr);
			mergeList.addAll(factor.getChangeList());
		}

		StringCommonFactor.sortDiffSequence(mergeList);

		int position = 0;
		StringBuffer strBuf = new StringBuffer();

		for (DiffSequence act : mergeList) {
			if (act.getStart() > position) {
				strBuf.append(original.substring(position, act.getStart()));
				position = act.getStart();
			}

			if (act.getAction().equals(StringCommonFactor.ACTION_INSERT)) {
				strBuf.append(act.getContent());
			} else if (act.getAction().equals(StringCommonFactor.ACTION_REMOVE)) {
				position = act.getEnd() + 1;
			}
		}

		if (position < original.length()) {
			strBuf.append(original.substring(position, original.length()));
		}

		mergeStr = strBuf.toString();

		return mergeStr;
	}

}
