package org.silvercatcher.reforged.packet;

/**
 * Thanks to Jabelar!!!
 */
public class MessageCustomReachAttack {

	private int entityId;

	public MessageCustomReachAttack() {

	}

	public MessageCustomReachAttack(int entityId) {
		this.entityId = entityId;
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

}