package sdong.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StringCommonFactor {

	String str1;
	String str2;
	int maxStr1;
	int maxStr2;
	int strGap;
	Map<Integer, List<ChildString>> childStringMapX;
	Map<Integer, List<ChildString>> childStringMapY;
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
		// 排序
		// sort_line(childStrings);
		// filter(childStrings);

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

	public Map<Integer, ChildString> getMapOld() {
		Map<Integer, ChildString> map = new HashMap<Integer, ChildString>();
		Position current = new Position();
		List<ChildString> childStringList;
		ChildString childString;
		for (int y = 0; y < maxStr2; y++) {
			if (childStringMapY.containsKey(y) && y >= current.y) {
				childStringList = childStringMapY.get(y);
				childString = null;
				for (ChildString s : childStringList) {
					if (s.x >= current.x && s.y >= current.y) {
						if (childString == null) {
							childString = s;
						} else if (s.length > childString.length) {
							childString = s;
						}
					}
				}
				if (childString != null) {
					map.put(childString.y, childString);
					current.setX(childString.x + childString.length);
					current.setY(childString.y + childString.length);
				}
			}
		}

		return map;

	}

	public Map<Integer, ChildString> getMap() {
		Map<Integer, ChildString> map = new HashMap<Integer, ChildString>();
		ChildString childString = null;
		int x = 0;
		int y = 0;
		do {
			childString = getClosedChildString(x, y);
			if (childString != null) {
				map.put(childString.y, childString);
				x = childString.x + childString.length;
				y = childString.y + childString.length;
			}

		} while (childString != null);

		return map;

	}

	private ChildString getClosedChildString(int i, int j) {
		ChildString childStringX = null;
		ChildString childStringY = null;
		ChildString childString = null;
		int x = i;
		int y = j;
		while (x < maxStr1 && y < maxStr2 && childString == null) {
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

			if (childStringMapY.containsKey(y)) {
				for (ChildString s : childStringMapY.get(y)) {
					if (s.x >= x && s.y >= y) {
						if (childStringY == null) {
							childStringY = s;
						} else if (s.length > childStringY.length) {
							childStringY = s;
						} else if (s.length == childStringY.length && s.distence < childStringY.distence) {
							childStringY = s;
						}
					}
				}
			}

			if (childStringX != null) {
				if (childStringY != null) {
					if (childStringX.length > childStringY.length) {
						childString = childStringX;
					} else if (childStringX.length == childStringY.length) {
						if (childStringX.distence <= childStringY.distence) {
							childString = childStringX;
						} else {
							childString = childStringY;
						}
					} else {
						childString = childStringY;
					}
				} else {
					childString = childStringX;
				}
			} else if (childStringY != null) {
				childString = childStringY;
			}

			x = x + 1;
			y = y + 1;
		}

		return childString;
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
		childStringMapY = new HashMap<Integer, List<ChildString>>();
		List<ChildString> childStrings;
		for (ChildString s : childStringList) {
			if (childStringMapX.containsKey(s.x)) {
				childStringMapX.get(s.x).add(s);
			} else {
				childStrings = new ArrayList<ChildString>();
				childStrings.add(s);
				childStringMapX.put(s.x, childStrings);
			}

			if (childStringMapY.containsKey(s.y)) {
				childStringMapY.get(s.y).add(s);
			} else {
				childStrings = new ArrayList<ChildString>();
				childStrings.add(s);
				childStringMapY.put(s.y, childStrings);
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
	private void sort(List<ChildString> list) {
		Collections.sort(list, new Comparator<ChildString>() {
			public int compare(ChildString o1, ChildString o2) {
				return o2.length - o1.length;
			}
		});

	}

	private void filter(List<ChildString> list) {
		ChildString childString;
		Iterator<ChildString> iterator = list.iterator();
		while (iterator.hasNext()) {
			childString = iterator.next();
			if (childString.length == 0) {
				iterator.remove();
			}
		}
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

		public Position getEndPosition() {
			return new Position(x + length, x + length);
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

	class Position {
		int x;
		int y;

		Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

		Position() {

		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
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

	public Map<Integer, List<ChildString>> getChildStringMapY() {
		return childStringMapY;
	}

	public void setChildStringMapY(Map<Integer, List<ChildString>> childStringMapY) {
		this.childStringMapY = childStringMapY;
	}

	public List<ChildString> getChildStringList() {
		return childStringList;
	}

	public void setChildStringList(List<ChildString> childStringList) {
		this.childStringList = childStringList;
	}

	/*
	 * public List<DiffSequence> getDiffSequence() { List<DiffSequence> diffs = new
	 * ArrayList<DiffSequence>(); int maxStr1 = str1.length(); int maxStr2 =
	 * str2.length(); Map<Integer, ChildString> map = new HashMap<Integer,
	 * ChildString>(); int currentPosition = 0; int gap = 0; for (ChildString s :
	 * childString) { if (map.containsKey(s.x)) { gap = s.length -
	 * map.get(s.x).length; if (gap > 0) { map.put(s.x, s); currentPosition =
	 * currentPosition + gap; } } else if (s.x > currentPosition) { map.put(s.x, s);
	 * currentPosition = s.length; } }
	 * 
	 * return diffs; }
	 */
}
