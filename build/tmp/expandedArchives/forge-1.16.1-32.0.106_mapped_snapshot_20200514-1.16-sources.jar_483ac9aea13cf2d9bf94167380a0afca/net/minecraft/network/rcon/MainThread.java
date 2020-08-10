package net.minecraft.network.rcon;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import net.minecraft.server.dedicated.ServerProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainThread extends RConThread {
   private static final Logger field_232652_d_ = LogManager.getLogger();
   private final int rconPort;
   private String hostname;
   private ServerSocket serverSocket;
   private final String rconPassword;
   private final List<ClientThread> clientThreads = Lists.newArrayList();
   private final IServer field_232653_j_;

   public MainThread(IServer p_i1538_1_) {
      super("RCON Listener");
      this.field_232653_j_ = p_i1538_1_;
      ServerProperties serverproperties = p_i1538_1_.getServerProperties();
      this.rconPort = serverproperties.rconPort;
      this.rconPassword = serverproperties.rconPassword;
      this.hostname = p_i1538_1_.getHostname();
      if (this.hostname.isEmpty()) {
         this.hostname = "0.0.0.0";
      }

   }

   /**
    * Cleans up the clientThreads map by removing client Threads that are not running
    */
   private void cleanClientThreadsMap() {
      this.clientThreads.removeIf((p_232654_0_) -> {
         return !p_232654_0_.isRunning();
      });
   }

   public void run() {
      field_232652_d_.info("RCON running on {}:{}", this.hostname, this.rconPort);

      try {
         while(this.running) {
            try {
               Socket socket = this.serverSocket.accept();
               ClientThread clientthread = new ClientThread(this.field_232653_j_, this.rconPassword, socket);
               clientthread.startThread();
               this.clientThreads.add(clientthread);
               this.cleanClientThreadsMap();
            } catch (SocketTimeoutException sockettimeoutexception) {
               this.cleanClientThreadsMap();
            } catch (IOException ioexception) {
               if (this.running) {
                  field_232652_d_.info("IO exception: ", (Throwable)ioexception);
               }
            }
         }
      } finally {
         this.func_232655_a_(this.serverSocket);
      }

   }

   /**
    * Creates a new Thread object from this class and starts running
    */
   public void startThread() {
      if (this.rconPassword.isEmpty()) {
         field_232652_d_.warn("No rcon password set in server.properties, rcon disabled!");
      } else if (0 < this.rconPort && 65535 >= this.rconPort) {
         if (!this.running) {
            try {
               this.serverSocket = new ServerSocket(this.rconPort, 0, InetAddress.getByName(this.hostname));
               this.serverSocket.setSoTimeout(500);
               super.startThread();
            } catch (IOException ioexception) {
               field_232652_d_.warn("Unable to initialise rcon on {}:{}", this.hostname, this.rconPort, ioexception);
            }

         }
      } else {
         field_232652_d_.warn("Invalid rcon port {} found in server.properties, rcon disabled!", (int)this.rconPort);
      }
   }

   public void func_219591_b() {
      this.running = false;
      this.func_232655_a_(this.serverSocket);
      super.func_219591_b();

      for(ClientThread clientthread : this.clientThreads) {
         if (clientthread.isRunning()) {
            clientthread.func_219591_b();
         }
      }

      this.clientThreads.clear();
   }

   private void func_232655_a_(ServerSocket p_232655_1_) {
      field_232652_d_.debug("closeSocket: {}", (Object)p_232655_1_);

      try {
         p_232655_1_.close();
      } catch (IOException ioexception) {
         field_232652_d_.warn("Failed to close socket", (Throwable)ioexception);
      }

   }
}