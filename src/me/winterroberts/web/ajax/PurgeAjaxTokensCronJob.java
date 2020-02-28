package me.winterroberts.web.ajax;

import net.aionstudios.jdc.cron.CronDateTime;
import net.aionstudios.jdc.cron.CronJob;
import net.aionstudios.jdc.util.DatabaseUtils;

public class PurgeAjaxTokensCronJob extends CronJob {

	public PurgeAjaxTokensCronJob() {
		super(new CronDateTime());
	}
	
	private String removeTokenAgeQuery = "DELETE FROM `portfolio_ajax`.`ajax_tokens` WHERE created < (NOW() - INTERVAL 89 MINUTE);";

	@Override
	public void run() {
		DatabaseUtils.prepareAndExecute(removeTokenAgeQuery, false);
	}

}
