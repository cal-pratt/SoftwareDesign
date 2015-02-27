package inputpkg;

// 3rd Part Imports ---------------------------------------------------------------------------- //
import static org.lwjgl.glfw.GLFW.*;
import java.util.Map;
import java.util.HashMap;

// Class definition ---------------------------------------------------------------------------- //
class KeyboardInput implements IKeyboardInput {
    
    // Keyboard key construct ------------------------------------------------------------------ //
    private class Key{
        public int action; // GLFW_RELEASE or GLFW_PRESS or GLFW_REPEAT
        public Key(){
            this.action = GLFW_RELEASE;
        }
        public Key(int action){
            this.action = action;
        }
    }

    // Keyboard data container ----------------------------------------------------------------- //
    private Map<Integer, Key> keyMap;

    // Constructors ---------------------------------------------------------------------------- //
    public KeyboardInput(){
        this.keyMap = defaultKeyMap();
    }
    
    public KeyboardInput(Map<Integer, Key> keyMap){
        this.keyMap = new HashMap<Integer, Key>();
        this.keyMap.putAll(keyMap);
    }
    
    public KeyboardInput clone(){
        return new KeyboardInput(this.keyMap);
    }
    
    // Accessors and Mutators ------------------------------------------------------------------ //
    public void keyInvoke(int key, int scancode, int action, int mods){
        keyMap.put(key, new Key(action));
    }
    
    public int getAction(int key){
        return keyMap.get(key).action;
    }
    
    // Default keyboard data ------------------------------------------------------------------- //
    private Map<Integer, Key> defaultKeyMap(){
        Map<Integer, Key> defaultMap = new HashMap<Integer, Key>();
        
        defaultMap.put(GLFW_KEY_UNKNOWN, new Key());
        defaultMap.put(GLFW_KEY_SPACE, new Key());
        defaultMap.put(GLFW_KEY_APOSTROPHE, new Key());
        defaultMap.put(GLFW_KEY_COMMA, new Key());
        defaultMap.put(GLFW_KEY_MINUS, new Key());
        defaultMap.put(GLFW_KEY_PERIOD, new Key());
        defaultMap.put(GLFW_KEY_SLASH, new Key());
        defaultMap.put(GLFW_KEY_0, new Key());
        defaultMap.put(GLFW_KEY_1, new Key());
        defaultMap.put(GLFW_KEY_2, new Key());
        defaultMap.put(GLFW_KEY_3, new Key());
        defaultMap.put(GLFW_KEY_4, new Key());
        defaultMap.put(GLFW_KEY_5, new Key());
        defaultMap.put(GLFW_KEY_6, new Key());
        defaultMap.put(GLFW_KEY_7, new Key());
        defaultMap.put(GLFW_KEY_8, new Key());
        defaultMap.put(GLFW_KEY_9, new Key());
        defaultMap.put(GLFW_KEY_SEMICOLON, new Key());
        defaultMap.put(GLFW_KEY_EQUAL, new Key());
        defaultMap.put(GLFW_KEY_A, new Key());
        defaultMap.put(GLFW_KEY_B, new Key());
        defaultMap.put(GLFW_KEY_C, new Key());
        defaultMap.put(GLFW_KEY_D, new Key());
        defaultMap.put(GLFW_KEY_E, new Key());
        defaultMap.put(GLFW_KEY_F, new Key());
        defaultMap.put(GLFW_KEY_G, new Key());
        defaultMap.put(GLFW_KEY_H, new Key());
        defaultMap.put(GLFW_KEY_I, new Key());
        defaultMap.put(GLFW_KEY_J, new Key());
        defaultMap.put(GLFW_KEY_K, new Key());
        defaultMap.put(GLFW_KEY_L, new Key());
        defaultMap.put(GLFW_KEY_M, new Key());
        defaultMap.put(GLFW_KEY_N, new Key());
        defaultMap.put(GLFW_KEY_O, new Key());
        defaultMap.put(GLFW_KEY_P, new Key());
        defaultMap.put(GLFW_KEY_Q, new Key());
        defaultMap.put(GLFW_KEY_R, new Key());
        defaultMap.put(GLFW_KEY_S, new Key());
        defaultMap.put(GLFW_KEY_T, new Key());
        defaultMap.put(GLFW_KEY_U, new Key());
        defaultMap.put(GLFW_KEY_V, new Key());
        defaultMap.put(GLFW_KEY_W, new Key());
        defaultMap.put(GLFW_KEY_X, new Key());
        defaultMap.put(GLFW_KEY_Y, new Key());
        defaultMap.put(GLFW_KEY_Z, new Key());
        defaultMap.put(GLFW_KEY_LEFT_BRACKET, new Key());
        defaultMap.put(GLFW_KEY_BACKSLASH, new Key());
        defaultMap.put(GLFW_KEY_RIGHT_BRACKET, new Key());
        defaultMap.put(GLFW_KEY_GRAVE_ACCENT, new Key());
        defaultMap.put(GLFW_KEY_WORLD_1, new Key());
        defaultMap.put(GLFW_KEY_WORLD_2, new Key());
        defaultMap.put(GLFW_KEY_ESCAPE, new Key());
        defaultMap.put(GLFW_KEY_ENTER, new Key());
        defaultMap.put(GLFW_KEY_TAB, new Key());
        defaultMap.put(GLFW_KEY_BACKSPACE, new Key());
        defaultMap.put(GLFW_KEY_INSERT, new Key());
        defaultMap.put(GLFW_KEY_DELETE, new Key());
        defaultMap.put(GLFW_KEY_RIGHT, new Key());
        defaultMap.put(GLFW_KEY_LEFT, new Key());
        defaultMap.put(GLFW_KEY_DOWN, new Key());
        defaultMap.put(GLFW_KEY_UP, new Key());
        defaultMap.put(GLFW_KEY_PAGE_UP, new Key());
        defaultMap.put(GLFW_KEY_PAGE_DOWN, new Key());
        defaultMap.put(GLFW_KEY_HOME, new Key());
        defaultMap.put(GLFW_KEY_END, new Key());
        defaultMap.put(GLFW_KEY_CAPS_LOCK, new Key());
        defaultMap.put(GLFW_KEY_SCROLL_LOCK, new Key());
        defaultMap.put(GLFW_KEY_NUM_LOCK, new Key());
        defaultMap.put(GLFW_KEY_PRINT_SCREEN, new Key());
        defaultMap.put(GLFW_KEY_PAUSE, new Key());
        defaultMap.put(GLFW_KEY_F1, new Key());
        defaultMap.put(GLFW_KEY_F2, new Key());
        defaultMap.put(GLFW_KEY_F3, new Key());
        defaultMap.put(GLFW_KEY_F4, new Key());
        defaultMap.put(GLFW_KEY_F5, new Key());
        defaultMap.put(GLFW_KEY_F6, new Key());
        defaultMap.put(GLFW_KEY_F7, new Key());
        defaultMap.put(GLFW_KEY_F8, new Key());
        defaultMap.put(GLFW_KEY_F9, new Key());
        defaultMap.put(GLFW_KEY_F10, new Key());
        defaultMap.put(GLFW_KEY_F11, new Key());
        defaultMap.put(GLFW_KEY_F12, new Key());
        defaultMap.put(GLFW_KEY_F13, new Key());
        defaultMap.put(GLFW_KEY_F14, new Key());
        defaultMap.put(GLFW_KEY_F15, new Key());
        defaultMap.put(GLFW_KEY_F16, new Key());
        defaultMap.put(GLFW_KEY_F17, new Key());
        defaultMap.put(GLFW_KEY_F18, new Key());
        defaultMap.put(GLFW_KEY_F19, new Key());
        defaultMap.put(GLFW_KEY_F20, new Key());
        defaultMap.put(GLFW_KEY_F21, new Key());
        defaultMap.put(GLFW_KEY_F22, new Key());
        defaultMap.put(GLFW_KEY_F23, new Key());
        defaultMap.put(GLFW_KEY_F24, new Key());
        defaultMap.put(GLFW_KEY_F25, new Key());
        defaultMap.put(GLFW_KEY_KP_0, new Key());
        defaultMap.put(GLFW_KEY_KP_1, new Key());
        defaultMap.put(GLFW_KEY_KP_2, new Key());
        defaultMap.put(GLFW_KEY_KP_3, new Key());
        defaultMap.put(GLFW_KEY_KP_4, new Key());
        defaultMap.put(GLFW_KEY_KP_5, new Key());
        defaultMap.put(GLFW_KEY_KP_6, new Key());
        defaultMap.put(GLFW_KEY_KP_7, new Key());
        defaultMap.put(GLFW_KEY_KP_8, new Key());
        defaultMap.put(GLFW_KEY_KP_9, new Key());
        defaultMap.put(GLFW_KEY_KP_DECIMAL, new Key());
        defaultMap.put(GLFW_KEY_KP_DIVIDE, new Key());
        defaultMap.put(GLFW_KEY_KP_MULTIPLY, new Key());
        defaultMap.put(GLFW_KEY_KP_SUBTRACT, new Key());
        defaultMap.put(GLFW_KEY_KP_ADD, new Key());
        defaultMap.put(GLFW_KEY_KP_ENTER, new Key());
        defaultMap.put(GLFW_KEY_KP_EQUAL, new Key());
        defaultMap.put(GLFW_KEY_LEFT_SHIFT, new Key());
        defaultMap.put(GLFW_KEY_LEFT_CONTROL, new Key());
        defaultMap.put(GLFW_KEY_LEFT_ALT, new Key());
        defaultMap.put(GLFW_KEY_LEFT_SUPER, new Key());
        defaultMap.put(GLFW_KEY_RIGHT_SHIFT, new Key());
        defaultMap.put(GLFW_KEY_RIGHT_CONTROL, new Key());
        defaultMap.put(GLFW_KEY_RIGHT_ALT, new Key());
        defaultMap.put(GLFW_KEY_RIGHT_SUPER, new Key());
        defaultMap.put(GLFW_KEY_MENU, new Key());
        
        return defaultMap;
    }
}
// --------------------------------------------------------------------------------------------- //

