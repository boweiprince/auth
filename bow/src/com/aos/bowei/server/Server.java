package com.aos.bowei.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * 
 * @author duanbowei
 *
 */
public class Server {

	ServerSocket ss ;
	int port = 9527 ;
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public Server() {
		init();
	}
	public Server(int port ) {
		this.port = port;
		init();
	}
	 
	public void init(){
		try {
			ss = new ServerSocket(port);
			System.out.println("Server start !");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
		}
	}
	
	public void run() {
		while(true) {
			Socket s ;
			try {
				System.out.println("wait client connection ... ");
				s = ss.accept();
				System.out.println("client connectioned ");
				
				new CilentThread(s).start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class CilentThread extends Thread{
		Socket s;
		
		private PrintWriter pw=null;
		private BufferedReader br=null;
		
		public CilentThread(Socket s) {
			this.s = s;
		} 
		
		public void run() {
			try {
				pw = new PrintWriter(s.getOutputStream());
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				
				String message ;
				while (true ) {
					message = br.readLine();
					if(message == null)
						break;
					if("exit".equals(message) || "quit".equals(message))
						return;
					
					System.out.println("client message : "+message);
					
					pw.println(sdf.format(new Date()) + " : "+ message );
					pw.flush();
					
					BufferedReader bbbr = null;
					String line ;
					try { 
						Process p  = Runtime.getRuntime().exec(message);
						int a = p.waitFor();
						System.out.println(a);
						bbbr = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
						while ((line = bbbr.readLine()) != null) {
							System.out.println(line);
							pw.println(line);
						}
						
					}catch (Exception e) {
						pw.println(message + " commond is not executable!");
					}finally {
						if(bbbr != null)
							bbbr.close();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
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
	}
	
	public static void main(String[] args) {
		Server s;
		if(args.length>0) {
			Integer po = Integer.valueOf(args[0]);
			s = new Server(po) ;
		}else {
			s = new Server(9000) ;
		}
		s.run();
	}
}
