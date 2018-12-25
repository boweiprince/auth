package com.aos.bowei.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

/***
 * 
 * @author duanbowei
 *
 */
public class Client {
	Socket s;
	String host = "127.0.0.1";
	int port = 9527;
	
	private  PrintWriter pw=null;
	private  BufferedReader br=null;
	
	public Client() {
		init();
	}
	
	public Client(String host,int port) {
		this.host = host ;
		this.port = port ;
		init();
	}
	private void init () {
		try {
			System.out.println("connection server ("+host+"). port:"+port+".. ");
			s = new Socket(host, port);
			
			pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(s.getInputStream())); 
					
			s.getOutputStream();
			
			System.out.println("connection server success!");
		}  catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run () {
		
		if(s == null) {
			System.out.println("Error ! can't connection server ("+host+":"+port+")! please check !");
//			return ;
		}
		
		
		Scanner sc = new Scanner(System.in);
		String line ;
		try {
			while(true) {
				line = sc.nextLine();
				
				pw.println(line);
				pw.flush();
				
				line = br.readLine();
				System.out.println(line);
			}
		} catch(SocketException e) {
			e.printStackTrace();
			System.out.println("Error ! server had reset! ");
			return ;
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			sc.close();
			try {
				pw.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static void main(String[] args) {
		
		String ip ;
		int port ;
		if(args.length >=2 ) {
			ip = args[0];
			port = Integer.valueOf(args[1]);
		}else {
			System.out.println("Sorry , need ip and port ! connection localhost ......9527");
			ip = "localhost";
			port = 9527;
		}
		
		Client c = new Client(ip, port);
		c.run();
		///bin/sh -c date
	}
}
