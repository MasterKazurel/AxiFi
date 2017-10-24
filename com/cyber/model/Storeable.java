package com.cyber.model;

import java.io.Serializable;

public abstract class Storeable<Entity> implements Serializable {
	private static final long serialVersionUID = 1L;

	public abstract boolean save();
}
