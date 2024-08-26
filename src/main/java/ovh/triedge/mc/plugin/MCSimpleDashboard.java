package ovh.triedge.mc.plugin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;

import com.sun.net.httpserver.HttpServer;

import ovh.triedge.mc.plugin.web.WebHandler;

import org.bukkit.plugin.java.JavaPlugin;

public class MCSimpleDashboard extends JavaPlugin{
	
	HttpServer server;
	
	public static final int DEFAULT_PORT			= 7867;
	public static final int DEFAULT_THREADS			= 5;

	@Override
	public void onEnable() {
		getLogger().log(Level.INFO,"Enable plugin MCSimpleDashboard");
		super.onEnable();
		
		try {
			int port = DEFAULT_PORT;
			server = HttpServer.create(new InetSocketAddress(port), 0);
			
			ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(DEFAULT_THREADS);
			server.createContext("/", new WebHandler(this));
			server.setExecutor(threadPoolExecutor);
			server.start();
			getLogger().log(Level.INFO,"Started web server on port: "+port+ " and threads: "+DEFAULT_THREADS);
		} catch (IOException e) {
			e.printStackTrace();
			getLogger().log(Level.SEVERE,"Failed to start web server: "+e.getMessage());
		}
	}
	
	@Override
	public void onDisable() {
		getLogger().log(Level.INFO,"Disabling plugin MCSimpleDashboard");
		super.onDisable();
		getServer().getScheduler().cancelTasks(this);
		if (server != null) {
			server.stop(0);
		}
	}
}
