package sdong.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringCommonFactor {

	public static final String ACTION_INSERT = "insert";
	public static final String ACTION_REMOVE = "remove";

	String str1;
	String str2;
	int maxStr1;
	int maxStr2;
	int strGap;
	Map<Integer, List<ChildString>> childStringMapX;
	List<ChildString> childStringList;
	int[][] array;

	StringCommonFactor(String str1, String str2) {
		this.str1 = str1;
		this.str2 = str2;

		childStringList = getStringMaxCommmon(str1, str2);
		setChildStringMap();
	}

	public List<ChildString> getStringMaxCommmon(String str1, String str2) {
		// 求所有公因子字符串，保存信息为相对第二个字符串的起始位置和长度
		List<ChildString> childStrings = new ArrayList<ChildString>();

		if (str1 == null || str2 == null || str1.isEmpty() || str2.isEmpty()) {
			return childStrings;
		}

		maxStr1 = str1.length();
		maxStr2 = str2.length();
		strGap = Math.abs(maxStr1 - maxStr2);

		array = new int[maxStr1][maxStr2];
		for (int i = 0; i < maxStr1; i++) {
			for (int j = 0; j < maxStr2; j++) {
				if (str1.charAt(i) == str2.charAt(j)) {
					array[i][j] = 1;
				} else {
					array[i][j] = 0;
				}
			}
		}

		// verify array
		String output;
		for (int i = 0; i < maxStr1; i++) {
			output = "";
			for (int j = 0; j < maxStr2; j++) {
				output = output + array[i][j] + ",";
			}
			System.out.println(output);
		}

		// verify distance
		for (int i = 0; i < maxStr1; i++) {
			output = "";
			for (int j = 0; j < maxStr2; j++) {
				output = output + "," + getDistance(i, j);
			}
			System.out.println(output);
		}

		for (int i = 0; i < maxStr1; i++) {
			getMaxSort(i, 0, maxStr1, maxStr2, array, childStrings);
		}
		if (maxStr2 > maxStr1) {
			for (int i = 1; i <= strGap; i++) {
				getMaxSort(0, i, maxStr1, maxStr2, array, childStrings);
			}
		}

		return childStrings;

	}

	// 求一条斜线上的公因子字符串
	private void getMaxSort(int i, int j, int maxStr1, int maxStr2, int[][] array, List<ChildString> childList) {
		int length = 0;
		int start = j;
		for (; i < maxStr1 && j < maxStr2; i++, j++) {
			if (array[i][j] == 1) {
				length++;
			} else {
				if (length != 0) {
					childList.add(new ChildString(i - length, start, length));
					length = 0;
				}
				start = j + 1;
			}
			if ((i == maxStr1 - 1 || j == maxStr2 - 1) && length != 0) {
				childList.add(new ChildString(i - length + 1, start, length));
			}
		}
	}

	public List<DiffSequence> getChangeList(List<ChildString> childStringList) {
		List<DiffSequence> changeList = new ArrayList<DiffSequence>();
		DiffSequence act;

		if (childStringList == null || childStringList.isEmpty()) {
			act = new DiffSequence();
			act.setAction(ACTION_REMOVE);
			act.setStart(0);
			act.setEnd(maxStr1);
			act.setContent(str2);
			changeList.add(act);
			return changeList;
		}

		int positionX = 0;
		int positionY = 0;
		for (ChildString s : childStringList) {
			if (s.x > positionX) {
				act = new DiffSequence();
				act.setAction(ACTION_REMOVE);
				act.setStart(positionX);
				act.setEnd(s.x - 1);
				act.setContent("");
				changeList.add(act);

			}

			if (s.y > positionY) {
				act = new DiffSequence();
				act.setAction(ACTION_INSERT);
				act.setStart(positionX);
				act.setEnd(0);
				act.setContent(str2.substring(positionY, s.y));
				changeList.add(act);

			}
			positionX = s.x + s.length;
			positionY = s.y + s.length;
		}

		if (positionX < maxStr1) {
			act = new DiffSequence();
			act.setAction(ACTION_REMOVE);
			act.setStart(positionX);
			act.setEnd(maxStr1);
			act.setContent("");
			changeList.add(act);
		}

		if (positionY < maxStr2) {
			act = new DiffSequence();
			act.setAction(ACTION_INSERT);
			act.setStart(positionX);
			act.setEnd(0);
			act.setContent(str2.substring(positionY, maxStr2 - 1));
			changeList.add(act);

		}

		return changeList;

	}

	public List<ChildString> getMatchList() {
		List<ChildString> matchList = new ArrayList<ChildString>();
		ChildString childString = null;
		int x = 0;
		int y = 0;
		do {
			childString = getClosedChildString(x, y);
			if (childString != null) {
				matchList.add(childString);
				x = childString.x + childString.length;
				y = childString.y + childString.length;
			}

		} while (childString != null);

		return matchList;

	}

	private ChildString getClosedChildString(int i, int j) {
		ChildString childStringX = null;
		int x = i;
		int y = j;
		while (x < maxStr1 && y < maxStr2 && childStringX == null) {
			if (childStringMapX.containsKey(x)) {
				for (ChildString s : childStringMapX.get(x)) {
					if (s.x >= x && s.y >= y) {
						if (childStringX == null) {
							childStringX = s;
						} else if (s.length > childStringX.length) {
							childStringX = s;
						} else if (s.length == childStringX.length && s.distence < childStringX.distence) {
							childStringX = s;
						}
					}
				}
			}
			x = x + 1;
		}

		return childStringX;
	}

	private int getDistance(int x, int y) {
		int dis = 0;
		if ((x - y) == (maxStr1 - maxStr2)) {
			dis = 0;
		} else {
			if (maxStr1 > maxStr2) {
				dis = (x - strGap - y) * (x - strGap - y);
			} else {
				dis = (y - strGap - x) * (y - strGap - x);
			}
		}

		return dis;
	}

	private void setChildStringMap() {
		childStringMapX = new HashMap<Integer, List<ChildString>>();
		List<ChildString> childStrings;
		for (ChildString s : childStringList) {
			if (childStringMapX.containsKey(s.x)) {
				childStringMapX.get(s.x).add(s);
			} else {
				childStrings = new ArrayList<ChildString>();
				childStrings.add(s);
				childStringMapX.put(s.x, childStrings);
			}
		}
	}

	// 排序，倒叙
	private void sort_line(List<ChildString> list) {
		Collections.sort(list, new Comparator<ChildString>() {
			public int compare(ChildString o1, ChildString o2) {
				return o1.x - o2.x;
			}
		});

	}

	// 排序，倒叙
	public void sortDiffSequence(List<DiffSequence> list) {
		Collections.sort(list, new Comparator<DiffSequence>() {
			public int compare(DiffSequence o1, DiffSequence o2) {
				return o2.start - o1.start;
			}
		});

	}


	// 公因子类
	class ChildString {
		int x;
		int y;
		int length;
		int distence;

		ChildString(int x, int y, int length) {
			this.x = x;
			this.y = y;
			this.length = length;
			this.distence = getDistance(x, y);
		}

		@Override
		public String toString() {
			return "ChildString [ x=" + x + ", y=" + y + ", length=" + length + "]";
		}

	}

	class DiffSequence {
		String action;
		int start;
		int end;
		String content;

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public int getStart() {
			return start;
		}

		public void setStart(int start) {
			this.start = start;
		}

		public int getEnd() {
			return end;
		}

		public void setEnd(int end) {
			this.end = end;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

	}

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public Map<Integer, List<ChildString>> getChildStringMapX() {
		return childStringMapX;
	}

	public void setChildStringMapX(Map<Integer, List<ChildString>> childStringMapX) {
		this.childStringMapX = childStringMapX;
	}

	public List<ChildString> getChildStringList() {
		return childStringList;
	}

	public void setChildStringList(List<ChildString> childStringList) {
		this.childStringList = childStringList;
	}

}
