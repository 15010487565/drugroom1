package www.xcd.com.mylibrary.entity;

/**
 * Created by dell on 2015-11-23.
 */
public class GlobalParam {
    public final static boolean DEBUG = false;
    public final static String headurl = "http://img2.3lian.com/2014/f2/37/d/39.jpg";

    public final static String APPLICATIONID = "com.medicinedot.www.medicinedot";
    //绑定头URL   http://69.165.64.194/chongai/index.php?
//    public final static String IP = "http://47.95.239.122";
    public final static String IP = "http://101.201.235.27:8084";
    //登陆
    public final static String LOGIN = IP + "/Index/User/login";
    //登陆
    public final static String FORGETPASSWORD = IP + "/Index/User/ForgetPassword";
    //药店注册邀请码
    public final static String INVITECODE = IP + "/Index/User/checkinvitecode";
    //获取验证码
    public final static String GETVERIFICATIONCODE = IP + "/Index/User/sendPhoneCode";
    //注册
    public final static String REGISTER = IP + "/Index/User/register";
    //完善信息
    public final static String PERFECT = IP + "/Index/User/perfect";
    //首页轮播图
    public final static String BANNERIMG = IP + "/Index/Index/bannerimg";
    //首页数据
    public final static String HOMEDATA = IP + "/Index/Index/index";
    //修改密码
    public final static String UPPASSWORD = IP + "/Index/User/uppwd";
    //关于我们
    public final static String ABOUNT = IP + "/Index/Index/aboutus";
    //意见反馈
    public final static String OPINIONFEEDBACK = IP + "/Index/Index/suggestions";
    //我的会员城市列表
    public final static String MEVIPCITYLIST = IP + "/Index/Index/insider";
    //城市列表
    public final static String ALLCITYLIST = IP + "/Index/Index/citylist";
    //允许开通会员城市列表
    public final static String CITYLISTFORMEMBER = IP + "/Index/Index/citylistForMember";
    //会员订单信息
    public final static String CREATEORDER = IP + "/Index/Members/createorder";
    //获取个人信息
    public final static String GETUSERINFOFORID = IP + "/Index/User/getUserInfoForID";
    //供应商是否是会员
    public final static String ISMEMBERFORUSER = IP + "/Index/Index/isMemberForUser";
    //微信回调地址异步通知
    public final static String CERT_NOTIFYWEXIN = IP + "wxpay2/example/notify2.php";
    //获取会员价格列表
    public final static String GETMONEYFORLEVEL = IP + "/Index/Members/getMoneyForLevel";
    //消息通知
    public final static String GETMESSAGEINFORM = IP + "/Index/Index/message";
    //咨询
    public final static String INFORMATION = IP + "/Index/News/newslist";
    //咨询
    public final static String UPPHONE = IP + "/Index/User/upPhone";
}
