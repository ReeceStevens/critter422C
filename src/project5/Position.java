 /* EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * Reece Stevens
 * rgs835
 * 16340
 * Ajay Rastogi
 * asr2368
 * 16345
 * Slip days used: 0
 * Fall 2015
 */
package project5;

public class Position {
	private int x;
	private int y;
	
	public Position() {
		this.x = 0;
		this.y = 0;
	}

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isEqual(Position p) {
		return (p.getX() == this.x) && (p.getY() == this.y);
	}

	public boolean adjacent (Position p) {
		if (this.isEqual(p)) { return false; }
		if ((p.getX() - 1 == this.x) || (p.getX() == this.x) || (p.getX() + 1 == this.x)) {
			if ((p.getY() - 1 == this.y) || (p.getY() == this.y) || (p.getY() + 1 == this.y)) {
				return true;
			}
		}
		return false;	
	}
}
