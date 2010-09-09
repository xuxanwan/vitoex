#!/usr/bin/python
# coding=utf-8
'''
Created on 2010-9-10
@author: vito
'''
#字典示例:电话本应用.
#使用人名为键的字典,每人以另一字典,键phone,addr分别表示电话号和地址.
import string

people = {
          'Alice' : {
                   'phone' : '2341',
                   'addr' : 'Foo drive 23'
                   },
          'Beth' : {
                    'phone' : '9102',
                    'addr' : 'Bar street 42'
                    },
          'Cecil' : {
                     'phone' : '3158',
                     'addr' : 'Baz avenue 90'
                     }
          }

#描述性标签,在打印输出时用到
labels = {
          'phone' : 'phone number',
          'addr' : 'address'
          }

name = raw_input('Name: ')
request = raw_input('Request for phone(p), address(a) or all info(all)? (p/a/all) ')

if request == 'p' : key = 'phone'
if request == 'a' : key = 'addr'
if request == 'all' : key = 'all'

#original
#if name in people in people : print "%s's %s is %s." % \  
#    (name,labels[key],people[name][key])


#some amendments
nameMatch = None  #暂存匹配的规范名称
found = False  #查找存在与否的标志

if name in people : 
    nameMatch = name
    found = True
elif string.capwords(name, ' ') in people :
    nameMatch = string.capwords(name, ' ')
    found = True

if found : 
    if request == 'all' :
        print "%s's related info: phone number is %s, address is %s." % \
            (nameMatch,people[nameMatch]['phone'],people[nameMatch]['addr'])
    else :
        print "%s's %s is %s." % \
            (nameMatch,labels[key],people[nameMatch][key])
else :
    print 'No such record found!'