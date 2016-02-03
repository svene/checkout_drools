package org.example.drools.hello;

import org.drools.RuleBase;
import org.drools.WorkingMemory;
import org.drools.compiler.DroolsParserException;
import org.example.drools.hello.message.Message;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class RulesTest {

	Context context = new Context(
		"/org/example/drools/hello/actionRule.drl"
		,"/org/example/drools/hello/helloWorld.drl"
	);

	@Test
	public void shouldFireHelloWorld() throws IOException, DroolsParserException {
		RuleBase ruleBase = context.initialiseDrools();
		WorkingMemory workingMemory = context.initializeMessageObjects(ruleBase,
			Arrays.asList(this::createHelloWorld, this::createHighValue)
		);

		int expectedNumberOfRulesFired = 2;
		int actualNumberOfRulesFired = workingMemory.fireAllRules();
		assertThat(actualNumberOfRulesFired, is(expectedNumberOfRulesFired));
	}

	private void createHelloWorld(WorkingMemory workingMemory) {
		Message helloMessage = new Message();
		helloMessage.setType("Hello");
		workingMemory.insert(helloMessage);
	}

	private void createHighValue(WorkingMemory workingMemory) {
		Message highValue = new Message();
		highValue.setType("High value");
		highValue.setMessageValue(42);
		workingMemory.insert(highValue);
	}
}