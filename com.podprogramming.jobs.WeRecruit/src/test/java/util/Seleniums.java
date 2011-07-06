package util;

import org.openqa.selenium.WebElement;

import com.google.common.base.Function;

public class Seleniums {
    
    public static Function<? super WebElement, String> elemToText() {
        return new Function<WebElement, String>() {
            @Override
            public String apply(WebElement elem) {
                return elem.getText();
            }
        };
    }

}
