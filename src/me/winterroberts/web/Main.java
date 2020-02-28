package me.winterroberts.web;

import me.winterroberts.web.ajax.AjaxSet;
import me.winterroberts.web.ajax.GenerateAjaxTokenProcessor;
import me.winterroberts.web.ajax.PurgeAjaxTokensCronJob;
import me.winterroberts.web.session.PrintIDSessionProcessor;
import me.winterroberts.web.session.PurgeSessionsCronJob;
import me.winterroberts.web.session.SessionSet;
import me.winterroberts.web.session.ValidateSessionProcessor;
import net.aionstudios.jdc.JDC;
import net.aionstudios.jdc.cron.CronDateTime;
import net.aionstudios.jdc.cron.CronManager;
import net.aionstudios.jdc.util.DatabaseUtils;

public class Main extends JDC {

	@Override
	public void initialize() {
		/*Ajax*/
		AjaxSet as = new AjaxSet(getProcessorManager());
		GenerateAjaxTokenProcessor gatas = new GenerateAjaxTokenProcessor(as);
		
		/*Session*/
		SessionSet ss = new SessionSet(getProcessorManager());
		PrintIDSessionProcessor pidss = new PrintIDSessionProcessor(ss);
		ValidateSessionProcessor vss = new ValidateSessionProcessor(ss);
		
		PurgeAjaxTokensCronJob patcj = new PurgeAjaxTokensCronJob();
		patcj.setCronDateTime(new CronDateTime());
		PurgeSessionsCronJob pscj = new PurgeSessionsCronJob();
		pscj.setCronDateTime(new CronDateTime());
		CronManager.addJob(patcj);
		CronManager.addJob(pscj);
		CronManager.startCron();
		
		createDatabase();
		createTables();
	}
	
	private String createDatabase = "CREATE DATABASE `portfolio` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;";
	
	private void createDatabase() {
		DatabaseUtils.prepareAndExecute(createDatabase, false);
	}
	
	private String createWebSessionsTable = "CREATE TABLE `portfolio`.`web_sessions` (\r\n" + 
			"  `sessionID` varchar(64) NOT NULL,\r\n" + 
			"  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\r\n" + 
			"  `last` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\r\n" + 
			"  PRIMARY KEY (`sessionID`),\r\n" + 
			"  UNIQUE KEY `sessionID_UNIQUE` (`sessionID`)\r\n" + 
			") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;";
	
	private void createTables() {
		DatabaseUtils.prepareAndExecute(createWebSessionsTable, false);
	}

}
