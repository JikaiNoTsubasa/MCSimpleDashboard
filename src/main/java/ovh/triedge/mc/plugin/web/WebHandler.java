package ovh.triedge.mc.plugin.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ovh.triedge.mc.plugin.MCSimpleDashboard;
import ovh.triedge.mc.plugin.utils.SStats;

public class WebHandler implements HttpHandler{
	
	MCSimpleDashboard plugin;
	
	public WebHandler(MCSimpleDashboard plugin) {
		this.plugin = plugin;
		this.plugin.getLogger().info("Web handler created");
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		this.plugin.getLogger().info("Requested index!");
		String output = "";
		int code = 200;
		
		output = generatePage();
		//this.plugin.getLogger().info("output = "+output);
		OutputStream outputStream = exchange.getResponseBody();
		exchange.sendResponseHeaders(code, output.length());
        outputStream.write(output.getBytes());
        outputStream.flush();
        outputStream.close();
		
		
	}
	
	public String generatePage() {
		StringBuilder tmp = new StringBuilder();
		
		tmp.append("<!DOCTYPE html>\r\n"
				+ "<html lang=\"en\">\r\n"
				+ "  <head>\r\n"
				+ "    <meta charset=\"UTF-8\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "    <title>MC Dashboard</title>\r\n"
				+ "	<style>\r\n"
				+ "		html{\r\n"
				+ "			background-color: #151618;\r\n"
				+ "		}\r\n"
				+ "	\r\n"
				+ "		body{\r\n"
				+ "			background-color: #151618;\r\n"
				+ "			color: #E6E6E6;\r\n"
				+ "		}\r\n"
				+ "		\r\n"
				+ "		.panel{\r\n"
				+ "			margin-bottom: 20px;\r\n"
				+ "			margin-left: 50px;\r\n"
				+ "			background-color: #272727;\r\n"
				+ "			display: inline-block;\r\n"
				+ "			padding: 30px;\r\n"
				+ "			vertical-align: top;\r\n"
				+ "		}\r\n"
				+ "		\r\n"
				+ "		.h1title{\r\n"
				+ "			margin: 50px;\r\n"
				+ "		}\r\n"
				+ "		\r\n"
				+ "		.numbers{\r\n"
				+ "			color: #C54599;\r\n"
				+ "			display: block;\r\n"
				+ "			font-size: 40px;\r\n"
				+ "		}\r\n"
				+ "		\r\n"
				+ "		.playertable{\r\n"
				+ "			width: 400px;\r\n"
				+ "		}\r\n"
				+ "		\r\n"
				+ "		.playertable th{\r\n"
				+ "			font-weight: bold;\r\n"
				+ "			text-align: left;\r\n"
				+ "		}\r\n"
				+ "		\r\n"
				+ "		.colorHighlight{\r\n"
				+ "			color: #64F4D6;\r\n"
				+ "		}\r\n"
				+ "		\r\n"
				+ "		.levelbar{\r\n"
				+ "			box-sizing: content-box;\r\n"
				+ "		  height: 10px; /* Can be anything */\r\n"
				+ "		  position: relative;\r\n"
				+ "		  background: #555;\r\n"
				+ "		  width: 50px;\r\n"
				+ "		}\r\n"
				+ "		\r\n"
				+ "		.levelbar > span {\r\n"
				+ "		  display: block;\r\n"
				+ "		  height: 100%;\r\n"
				+ "		  background-color: #64F4D6;\r\n"
				+ "		  position: relative;\r\n"
				+ "		  overflow: hidden;\r\n"
				+ "		}\r\n"
				+ "		\r\n"
				+ "		.meter{\r\n"
				+ "		  box-sizing: content-box;\r\n"
				+ "		  height: 15px; /* Can be anything */\r\n"
				+ "		  position: relative;\r\n"
				+ "		  background: #555;\r\n"
				+ "		  width: 400px;\r\n"
				+ "		  margin-top: 20px;\r\n"
				+ "		}\r\n"
				+ "		\r\n"
				+ "		.meter > span {\r\n"
				+ "		  display: block;\r\n"
				+ "		  height: 100%;\r\n"
				+ "		  background-color: #64F4D6;\r\n"
				+ "		  position: relative;\r\n"
				+ "		  overflow: hidden;\r\n"
				+ "		}\r\n"
				+ "	</style>\r\n"
				+ "  </head>\r\n"
				+ "  <body>");
		
		tmp.append("<h1 class=\"h1title\">Minecraft Dashboard</h1>\r\n"
				+ "	<div class=\"panel\">\r\n"
				+ "		<p>CONNECTED</p>\r\n");
		
		List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
		tmp.append("<span class=\"numbers\">").append(list.size()).append("</span>\r\n");
		tmp.append("		<table class=\"playertable\">\r\n"
				+ "			<tr><th>Player</span></th><th>ID</th><th>Level</th><th>EXP</th><th>Health</th><th>Food</th></tr>");
		
		for (Player p : list) {
			tmp.append("<tr>");
			tmp.append("<td><span class=\"colorHighlight\">").append(p.getDisplayName()).append("</span></td>");
			tmp.append("<td>").append(p.getEntityId()).append("</td>");
			tmp.append("<td><span class=\"colorHighlight\">").append(p.getLevel()).append("</span></td>");
			int exp = (int) (p.getExp()*100);
			tmp.append("<td><div class=\"levelbar\"><span style=\"width: ").append(exp).append("%\"></span></div></td>");
			tmp.append("<td>").append(p.getHealth()).append("</td>");
			tmp.append("<td>").append(p.getFoodLevel()).append("</td>");
			
			
			tmp.append("</tr>");
		}
		
		tmp.append("</table>\r\n"
				+ "	</div>");
		
		
		tmp.append("<div class=\"panel\">\r\n"
				+ "		<p>JVM</p>\r\n"
				+ "		<span class=\"numbers\">");
		tmp.append(String.format("%.3f",(SStats.getMemoryUsed()/1024.0/1024.0/1024.0))).append("/").append(String.format("%.3f",(SStats.getMemoryMax()/1024.0/1024.0/1024.0))).append(" Gb</span>\r\n");
		
		tmp.append("		<div class=\"meter\">\r\n"
				+ "			<span style=\"width: "+SStats.getMemoryUsagePercent()+"%\"></span>\r\n"
				+ "		</div>\r\n"
				+ "	</div>");
		
		
		tmp.append("</body>\r\n"
				+ "</html>");
		
		return tmp.toString();
	}

}
