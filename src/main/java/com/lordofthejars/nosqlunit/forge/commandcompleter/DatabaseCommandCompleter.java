package com.lordofthejars.nosqlunit.forge.commandcompleter;

import static ch.lambdaj.Lambda.convert;

import org.jboss.forge.shell.completer.SimpleTokenCompleter;

import ch.lambdaj.function.convert.Converter;

import com.lordofthejars.nosqlunit.forge.DatabaseEnum;

public class DatabaseCommandCompleter extends SimpleTokenCompleter {

	@Override
	public Iterable<String> getCompletionTokens() {
		return convert(DatabaseEnum.values(), new Converter<DatabaseEnum, String>() {

			@Override
			public String convert(DatabaseEnum databaseEnum) {
				return databaseEnum.name();
			}
		});
	}

}
