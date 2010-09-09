#!/usr/bin/python
# coding=utf-8
'''
Created on 2010-9-2
@author: vito
'''
#序列成员资格示例
#检查用户名和pin码

database = [['albert','1234'], ['dilbert','2345'], ['smith', '3456'], ['jones', '4567']]
username = raw_input('Username: ')
pin = raw_input('PIN: ')

if [username, pin] in database : print 'Access Granted'
else : print 'Access Denied'