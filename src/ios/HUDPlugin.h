//
//  HUDPlugin.h
//  HelloHud
//
//  Created by Francesco Verheye on 03/12/14.
//
//

#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>

@interface HUDPlugin : CDVPlugin <UIAlertViewDelegate>

- (void)show:(CDVInvokedUrlCommand*)command;

- (void)hide:(CDVInvokedUrlCommand*)command;

- (void)isShowing:(CDVInvokedUrlCommand*)command;
@end
