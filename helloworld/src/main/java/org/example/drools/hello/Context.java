package org.example.drools.hello;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.DroolsError;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderErrors;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.Consumer;

public class Context {

	public RuleBase initialiseDrools() throws IOException, DroolsParserException {
		PackageBuilder packageBuilder = readRuleFiles();
		return addRulesToWorkingMemory(packageBuilder);
	}

	public WorkingMemory initializeMessageObjects(RuleBase ruleBase, Consumer<WorkingMemory> workingMemoryConsumer) {
		WorkingMemory workingMemory = ruleBase.newStatefulSession();
		workingMemoryConsumer.accept(workingMemory);
		return workingMemory;
	}

	private PackageBuilder readRuleFiles() throws DroolsParserException, IOException {
		PackageBuilder packageBuilder = new PackageBuilder();
		packageBuilder.addPackageFromDrl(getRuleFileAsReader("/org/example/drools/hello/helloWorld.drl"));
		handlePossibleErrors(packageBuilder);
		return packageBuilder;
	}

	private void handlePossibleErrors(PackageBuilder packageBuilder) {
		PackageBuilderErrors errors = packageBuilder.getErrors();

		if (errors.getErrors().length > 0) {
			StringBuilder errorMessages = new StringBuilder();
			errorMessages.append("Found errors in package builder\n");
			for (int i = 0; i < errors.getErrors().length; i++) {
				DroolsError errorMessage = errors.getErrors()[i];
				errorMessages.append(errorMessage);
				errorMessages.append("\n");
			}
			errorMessages.append("Could not parse knowledge");

			throw new IllegalArgumentException(errorMessages.toString());
		}
	}

	private Reader getRuleFileAsReader(String ruleResourcename) {
		InputStream is = getClass().getResourceAsStream(ruleResourcename);
		if (is == null) throw new RuntimeException("cannot read rules resource file");
		return new InputStreamReader(is);
	}

	private RuleBase addRulesToWorkingMemory(PackageBuilder packageBuilder) {
		RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		ruleBase.addPackage(packageBuilder.getPackage());

		return ruleBase;
	}



}
