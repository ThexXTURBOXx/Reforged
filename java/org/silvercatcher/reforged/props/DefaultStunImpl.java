package org.silvercatcher.reforged.props;

public class DefaultStunImpl implements IStunProperty {
	
	private boolean isStunned = false;
	
	@Override public boolean isStunned() {
		return isStunned;
	}
	
	@Override public void setStunned(boolean value) {
		this.isStunned = value;
	}
	
}