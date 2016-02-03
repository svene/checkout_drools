package org.example.drools.hello.action;

import org.example.drools.hello.message.Message;

public class Action {
	public void performAction(Message message) {
		message.printMessage();
	}
}

