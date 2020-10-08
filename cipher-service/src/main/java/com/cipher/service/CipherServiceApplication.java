package com.cipher.service;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.time.Month;
import java.util.Base64;

//@SpringBootApplication
public class CipherServiceApplication {

	private PrivateKey privateKey;
	private PublicKey publicKey;

	public CipherServiceApplication() throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		KeyPair pair = keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	public void writeToFile(String path, byte[] key) throws IOException {
		File f = new File(path);
		f.getParentFile().mkdirs();

		FileOutputStream fos = new FileOutputStream(f);
		fos.write(key);
		fos.flush();
		fos.close();
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}


	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		//SpringApplication.run(CipherServiceApplication.class, args);
		/*CipherServiceApplication keyPairGenerator = new CipherServiceApplication();
		keyPairGenerator.writeToFile("RSA/publicKey", keyPairGenerator.getPublicKey().getEncoded());
		keyPairGenerator.writeToFile("RSA/privateKey", keyPairGenerator.getPrivateKey().getEncoded());
		System.out.println(Base64.getEncoder().encodeToString(keyPairGenerator.getPublicKey().getEncoded()));
		System.out.println(Base64.getEncoder().encodeToString(keyPairGenerator.getPrivateKey().getEncoded()));*/

		TextIO textIO = TextIoFactory.getTextIO();

		String user = textIO.newStringInputReader()
				.withDefaultValue("admin")
				.read("Username");

		String password = textIO.newStringInputReader()
				.withMinLength(6)
				.withInputMasking(true)
				.read("Password");

		int age = textIO.newIntInputReader()
				.withMinVal(13)
				.read("Age");

		Month month = textIO.newEnumInputReader(Month.class)
				.read("What month were you born in?");

		TextTerminal terminal = textIO.getTextTerminal();
		terminal.printf("\nUser %s is %d years old, was born in %s and has the password %s.\n",
				user, age, month, password);
	}

}
