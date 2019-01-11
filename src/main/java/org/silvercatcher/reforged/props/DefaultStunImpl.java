package org.silvercatcher.reforged.props;

import java.util.concurrent.Callable;

public class DefaultStunImpl implements Callable<DefaultStunImpl>, IStunProperty {

	private boolean isStunned = false;

	public boolean isStunned() {
		return isStunned;
	}

	public void setStunned(boolean value) {
		this.isStunned = value;
	}

	@Override
	public DefaultStunImpl call() {
		return null;
	}
}