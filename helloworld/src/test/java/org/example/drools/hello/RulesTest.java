package org.example.drools.hello;

import org.drools.RuleBase;
import org.drools.WorkingMemory;
import org.drools.compiler.DroolsParserException;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class RulesTest {

	Context context = new Context();

	@Test
	public void shouldFireHelloWorld() throws IOException, DroolsParserException {
		RuleBase ruleBase = context.initialiseDrools();
		WorkingMemory workingMemory = context.initializeMessageObjects(ruleBase, this::createHelloWorld);

		int expectedNumberOfRulesFired = 1;
		int actualNumberOfRulesFired = workingMemory.fireAllRules();
		assertThat(actualNumberOfRulesFired, is(expectedNumberOfRulesFired));
	}

	private void createHelloWorld(WorkingMemory workingMemory) {
		Message helloMessage = new Message();
		helloMessage.setType("Hello");
		workingMemory.insert(helloMessage);
	}
}