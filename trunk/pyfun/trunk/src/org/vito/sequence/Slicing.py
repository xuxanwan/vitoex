#!/usr/bin/python
# coding=utf-8
'''
Created on 2010-8-25
@author: vito
'''

#分片示例,对如下格式的url进行分割
#http://www.somesite.com
url = raw_input('Pls input a url: ')
domain = url[11:-4]

print 'Domain name: ' + domain