/**
 * @author: created by wwbweibo
 * @version: 1.0
 * @date: 2019/4/12
 */

package se.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import se.Model.Mapper.NotificationMapper;
import se.Model.Notification;
import se.utils.DbUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this Controller contains the methods about notifications
 */
@Controller
public class NotifyController {
    /**
     * a page to show all notification sort by time
     *
     * @return page
     */
    @RequestMapping("/Notify")
    public String AllNotify() {
        DbUtils dbUtils = new DbUtils();
        NotificationMapper mapper = dbUtils.session.getMapper(NotificationMapper.class);
        List<Notification> notifications = mapper.selectAll();
        return null;
    }

    /**
     * return the top {top} notification in system
     *
     * @param top number to return
     * @return JSON string
     */
    @RequestMapping("/Notify/Top")
    public @ResponseBody
    List<Notification> TopNotify(@RequestParam int top) {
        DbUtils dbUtils = new DbUtils();
        NotificationMapper mapper = dbUtils.session.getMapper(NotificationMapper.class);
        List<Notification> notifications = mapper.selectTop(top);

        return notifications;
    }

    /**
     * to create a new notification, only for admin
     *
     * @param title title
     * @param text  context
     * @return if success redirect to /Notify, if false return null
     */
    @RequestMapping(value = "/Notify/New", method = RequestMethod.POST)
    public String New(@RequestParam String title, @RequestParam String text) {
        //todo: check the current user is admin
        //todo: write to database
        Date time = new Date();
        Notification notification = new Notification();
        notification.setTime(time);
        notification.setTitle(title);
        notification.setContext(text);

        DbUtils dbUtils = new DbUtils();
        NotificationMapper mapper = dbUtils.session.getMapper(NotificationMapper.class);
        mapper.newNotification(notification);
        dbUtils.session.commit();
        dbUtils.session.close();
        return null;
    }
}