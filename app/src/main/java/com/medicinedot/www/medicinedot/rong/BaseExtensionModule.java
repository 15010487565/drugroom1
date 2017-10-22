package com.medicinedot.www.medicinedot.rong;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.ImagePlugin;
import io.rong.imlib.model.Conversation;

/**
 * Created by Android on 2017/10/20.
 */

public class BaseExtensionModule extends DefaultExtensionModule {
//    private BasePlugin myPlugin;
    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules =  super.getPluginModules(conversationType);
//        pluginModules.add(myPlugin);
        pluginModules.clear();
        //添加一个发送图片的插件
        pluginModules.add(new ImagePlugin());
        //如果需要增加的话，那么同理，在这个集合中添加需要的插件
        return pluginModules;
    }
}
