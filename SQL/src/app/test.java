package app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
	
	public static void main(String[] args) {
		String test = "sdfsdf {{ test.maMethode }} qsdqsdqs";
		
		Pattern pattern = Pattern.compile(Pattern.quote("{{ ") + "(.*?)" + Pattern.quote(" }}"));
		
		System.out.println(pattern.pattern());
		
		Matcher matcher = pattern.matcher(test);
		
		System.out.println(matcher.groupCount());
		
		while (matcher.find()) {
			System.out.println("group : " + matcher.start() + ":" + matcher.end() + " : " + matcher.group(1));
		}
	}
}
