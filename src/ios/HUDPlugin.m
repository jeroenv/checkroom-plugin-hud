//
//  HUDPlugin.m
//  HelloHud
//
//  Created by Francesco Verheye on 03/12/14.
//
//

#import "HUDPlugin.h"
#import "MBProgressHUD.h"

@implementation HUDPlugin{
    NSString *_text;
    NSString *_icon;
    NSInteger _timeOut;
    
    MBProgressHUD *_hud;
    
    NSTimer *_closeHudTimer;
}

- (void)show:(CDVInvokedUrlCommand*)command
{
    NSMutableDictionary *options = [command argumentAtIndex:0];
    
    [self setDefaults];
    
    [self readParams:options];
    
    [self showHUD];
}

- (void)hide:(CDVInvokedUrlCommand*)command
{
    [self hideHud];
}

- (void)isShowing:(CDVInvokedUrlCommand*)command
{
    [self jsSendVisibility];
}

-(void) setDefaults
{
    _text = @"";
    _icon = @"";
    _timeOut = 0;
}

-(void) readParams:(NSDictionary*) params
{
    if([params objectForKey:@"text"])
    {
        _text = [params objectForKey:@"text"];
    }
    if([params objectForKey:@"icon"])
    {
        _icon = [params objectForKey:@"icon"];
    }
    if([params objectForKey:@"timeOut"])
    {
        _timeOut = [[params objectForKey:@"timeOut"] integerValue];
    }
}

-(void) showHUD
{
    [self showHudWithImage:_icon andText:_text];
}

-(void) showHudWithImage:(NSString*)imageName andText:(NSString*)text
{
    if (_hud == nil)
    {
        _hud = [MBProgressHUD showHUDAddedTo:[[UIApplication sharedApplication] keyWindow] animated:YES];
    };
    
    if(![imageName isEqualToString:@""])
    {
        _hud.mode = MBProgressHUDModeCustomView;
        _hud.customView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:imageName]];
    }
    
    if(text && ![text isEqualToString:@""])
    {
        _hud.labelText = text;
    }
    
    if(_timeOut != 0 && _timeOut > 0){
        _closeHudTimer = [NSTimer  scheduledTimerWithTimeInterval:_timeOut / 1000
                                                           target:self
                                                         selector:@selector(hideHud)
                                                         userInfo:nil
                                                          repeats:NO];
    }
}

-(void) hideHud
{
    if (_hud != nil)
    {
        [MBProgressHUD hideHUDForView:[[UIApplication sharedApplication] keyWindow] animated:YES];
        _hud = nil;
    }
    if(_closeHudTimer)
    {
        [_closeHudTimer invalidate];
        _closeHudTimer = nil;
    }
}

#pragma mark - JS API
-(void) jsSendVisibility
{
    NSString* jsCallback;
    if([_hud isHidden] || _hud == nil)
    {
        jsCallback= @"hudPlugin._isShowing(\"invisible\");";
    }
    else
    {
        jsCallback= @"hudPlugin._isShowing(\"visible\");";
    }
    [self.commandDelegate evalJs:jsCallback];
}

@end
