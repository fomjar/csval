package com.fomjar.test.csval;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fomjar.csval.CSVal;

class TestCSVal {
	
	private String 	lineSeparator 	= System.getProperty("line.separator");
	private CSVal 	csv 			= new CSVal();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {}

	@AfterAll
	static void tearDownAfterClass() throws Exception {}

	@BeforeEach
	void setUp() throws Exception {}

	@AfterEach
	void tearDown() throws Exception {
		csv.head(null);
		csv.body().clear();
		System.out.println("==========");
	}

	@Test
	void testRead() {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(("id, name" + lineSeparator + "1, jack" + lineSeparator + "2, helen").getBytes());
			csv.read(bais);
			bais.close();
			System.out.println(csv.toString());
		} catch (IOException e) {
			fail(e);
		}
	}
	
	@Test
	void testWrite() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			csv.read(new ByteArrayInputStream(("id, name" + lineSeparator + "1, jack" + lineSeparator + "2, helen").getBytes()));
			csv.write(baos);
			System.out.println(baos.toString());
			baos.close();
		} catch (IOException e) {
			fail(e);
		}
	}

}
