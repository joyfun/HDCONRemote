package hdcon.com.remote;

/**
 * Created by joyfun on 11/21/15.
 */
public class Consts {
   public static final int Comm_Password_Verify_Request = 100;		//请求校验密码
   public static final int Comm_Password_Verify_Result=101;		//返回密码校验结果
   public static final int Comm_Key_Event=102;						//键值输入
   public static final int Comm_Key_Call_Result=103;					//呼叫结果	后续包int类型，非0成功
    public static final int Comm_Key_End_Call_Result=104;				//挂断结果	后续包int类型，非0成功
    public static final int Comm_Live=105	;						//发送live包
    public static final int Comm_Live_Answer=106;						//回复live包
    public static final int Comm_Other_Connected=107;

    public static final int Key_F1 = 0x01000030 ;
    public static final int Key_F2 = 0x01000031 ;
    public static final int Key_F3 = 0x01000032 ;
    public static final int Key_F4 = 0x01000033 ;
    public static final int Key_F5 = 0x01000034 ;
    public static final int Key_F6 = 0x01000035 ;
    public static final int Key_F7 = 0x01000036 ;
    public static final int Key_F8 = 0x01000037 ;
    public static final int Key_F9 = 0x01000038 ;
    public static final int Key_F10 = 0x01000039 ;
    public static final int Key_F11 = 0x0100003a ;
    public static final int Key_F12 = 0x0100003b ;
    public static final int Key_0 = 0x30;
    public static final int Key_1 = 0x31;
    public static final int Key_2 = 0x32;
    public static final int Key_3 = 0x33;
    public static final int Key_4 = 0x34;
    public static final int Key_5 = 0x35;
    public static final int Key_6 = 0x36;
    public static final int Key_7 = 0x37;
    public static final int Key_8 = 0x38;
    public static final int Key_9 = 0x39;
    public static final int Key_A = 0x41;
    public static final int Key_B = 0x42;
    public static final int Key_C = 0x43;
    public static final int Key_D = 0x44;
    public static final int Key_E = 0x45;
    public static final int Key_F = 0x46;
    public static final int Key_G = 0x47;
    public static final int Key_H = 0x48;
    public static final int Key_I = 0x49;
    public static final int Key_J = 0x4a;
    public static final int Key_K = 0x4b;
    public static final int Key_L = 0x4c;
    public static final int Key_M = 0x4d;
    public static final int Key_N = 0x4e;
    public static final int Key_O = 0x4f;
    public static final int Key_P = 0x50;
    public static final int Key_Q = 0x51;
    public static final int Key_R = 0x52;
    public static final int Key_S = 0x53;
    public static final int Key_T = 0x54;
    public static final int Key_U = 0x55;
    public static final int Key_V = 0x56;
    public static final int Key_W = 0x57;
    public static final int Key_X = 0x58;
    public static final int Key_Y = 0x59;
    public static final int Key_Z = 0x5a;

    public static final int Key_Plus = 0x2b;			//+
    public static final int Key_Minus = 0x2d;			//-
    public static final int Key_QuoteLeft = 0x60;		//`
    public static final int Key_Escape = 0x01000000;	// esc
    public static final int Key_Return = 0x01000004;
    public static final int Key_Enter = 0x01000005;
    public static final int Key_Left = 0x01000012;		//left
    public static final int Key_Up = 0x01000013;		//up
    public static final int Key_Right = 0x01000014;		//right
    public static final int Key_Down = 0x01000015;		//down
    public static final int Key_Backspace = 0x01000003;
}
