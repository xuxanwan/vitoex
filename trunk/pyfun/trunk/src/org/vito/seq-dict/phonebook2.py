#!/usr/bin/python
# coding=utf-8
'''
Created on 2010-9-10
@author: vito
'''
#字典示例:电话本应用. 
#phonebook.py 的修改版本. 使用了get()方法的简单数据库.
#
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
request = raw_input('Request for phone(p), address(a)? (p/a) ')

key = request  # 如果请求不是 p 或 a.
if request == 'p' : key = 'phone'
if request == 'a' : key = 'addr'
#if request == 'all' : key = 'all'

#original
#使用get()提供默认值.
person = people.get(name, {})  # 返回值是一个字典
label = labels.get(key, "\"" + key + "\"")
result = person.get(key, 'not available')

print "%s's %s is %s." % (name, label, result)


#some amendments
