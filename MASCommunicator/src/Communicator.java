import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Communicator {

	private static Socket socket;
	
	public static void main(String[] args) {
		
		String name = "name";
		String host = "localhost";
		int port = 8000;
		String input = "";
		Scanner reader = new Scanner(System.in);
		String incomingMessage;
		String myMessage;
		
		while (true)
		{
			System.out.println("Chcete poslat zpravu(1) nebo cekat na zpravu(2) nebo ukoncit program(3)?");
			input = reader.nextLine();
			
			if (input.equals("1")) 			// poslani zpravy
			{
				
				try {
					InetAddress address = InetAddress.getByName(host);
					socket = new Socket(address, port);
					
					System.out.println("Zadejte zpravu: ");
					myMessage = reader.nextLine();
					
					OutputStream os = socket.getOutputStream();
					OutputStreamWriter writer = new OutputStreamWriter(os);
					BufferedWriter bw = new BufferedWriter(writer);
					
					InputStream is = socket.getInputStream();
					InputStreamReader isreader = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isreader);
					
					myMessage = name + ": " + myMessage.concat("\n");
					bw.write(myMessage);
					bw.flush();
					System.out.println("Odeslana zprava: " + myMessage + "\nCekam na potvrzeni..");
					
					incomingMessage = br.readLine();
					System.out.println("Odpoved: " + incomingMessage);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				finally
				{
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			else if (input.equals("2")) 	// prijeti zpravy
			{
				// TODO prijeti zpravy
				ServerSocket serverSocket = null;
				try {
					serverSocket = new ServerSocket(port);
					socket = serverSocket.accept();
					
					InputStream is = socket.getInputStream();
					InputStreamReader isreader = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isreader);
					
					OutputStream os = socket.getOutputStream();
					OutputStreamWriter writer = new OutputStreamWriter(os);
					BufferedWriter bw = new BufferedWriter(writer);
					
					incomingMessage = br.readLine();
					System.out.println("Prijata zprava: " + incomingMessage);
					
					// "KLICOVE_SLOVO;jmeno_odesilatele"
					if (incomingMessage.contains("SEND"))
					{
						bw.write(name + ": zprava obdrzena..");
						bw.flush();
					}
					else
					{
						
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if (input.equals("3"))
			{
				break;
			}
			else
			{
				System.out.println("Zadejte cislo 1, 2 nebo 3..");
			}
		}
		
		if (input.equals("a"))
		{
		}
		else
		{
			ServerSocket ss = null;
			try {
				ss = new ServerSocket(port);
				
				socket = ss.accept();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally
	        {
	            try
	            {
	                socket.close();
	                ss.close();
	            }
	            catch(Exception e)
	            {
	                e.printStackTrace();
	            }
	        }
		}
	}
}
