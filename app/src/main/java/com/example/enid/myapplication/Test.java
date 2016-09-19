package com.example.enid.myapplication;

import android.app.Activity;

import com.example.enid.myapplication.test.Base;

/**
 * Created by Enid on 2016/9/1.
 */
public class Test extends Activity{

    class Sub extends Base {
        @Override
        protected void funProtected() {
            super.funProtected();
        }

        @Override
        public void funPublic() {
            super.funPublic();
        }
    }

    void test() {
        Sub sub = new Sub();
        sub.funProtected();
        sub.funPublic();
    }
}
