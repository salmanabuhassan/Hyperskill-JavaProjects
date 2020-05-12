package encryptdecrypt;

import java.util.HashMap;

class Algo {
    private selectAlgo algorithm;
    public void setAlgorithm(selectAlgo algorithm) {
        this.algorithm = algorithm;
    }
    public HashMap encrypt(int key, HashMap mp) {
        return this.algorithm.encrypt(key,mp);
    }
    public HashMap decrypt(int key,HashMap mp) {
        return this.algorithm.decrypt(key,mp);
    }

}
interface selectAlgo {
    HashMap encrypt(int key,HashMap mp);
    HashMap decrypt(int key,HashMap mp);
}

class unicode implements selectAlgo {
    @Override
    public HashMap encrypt(int key,HashMap mp) {
        for (char c = 32; c <= 126; c++) {
            if((int)c + key > 126)
                mp.put(c,(char)((c+key)%127 + 32));
            else
                mp.put(c,(char)(c+key));
        }
        return mp;
    }

    @Override
    public HashMap decrypt(int key,HashMap mp) {
        for (char c = 32; c <= 126; c++) {
            if((int)c - key < 32)
                mp.put(c,(char)(127 - (32-((int)c - key))));
            else
                mp.put(c,(char)(c-key));
        }
        return mp;
    }
}
class shift implements selectAlgo {
    @Override
    public HashMap encrypt(int key, HashMap mp) {
        for (char c = 'a'; c <= 'z'; c++) {
            if((int)c + key > 122) {
                mp.put(c, (char) ((c + key) % 123 + 97));
                mp.put(Character.toUpperCase(c),
                        Character.toUpperCase((char)
                                ((c + key) % 123 + 97)));
            }
            else {
                mp.put(c, (char) (c + key));
                mp.put(Character.toUpperCase(c),
                        Character.toUpperCase((char) (c + key)));
            }
        }
        return mp;
    }

    @Override
    public HashMap decrypt(int key, HashMap mp) {
        for (char c = 'a'; c <= 'z'; c++) {
            int r = (int)c - key;
            if(r < 97) {
                mp.put(c, (char) (123 - (97-r)));
                mp.put(Character.toUpperCase(c),
                        Character.toUpperCase((char)
                                (123 - (97-r))));
            }
            else {
                mp.put(c, (char) (c - key));
                mp.put(Character.toUpperCase(c),
                        Character.toUpperCase((char) (c - key)));
            }
        }
        return mp;
    }
}
