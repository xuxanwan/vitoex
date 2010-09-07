#!/usr/bin/python
# coding=utf-8
'''
Created on 2010-9-8
@author: vito
'''
#字符串格式化示例.
#使用给定宽度,打印格式化后的价格列表.
#使用星号字段宽度说明符,格式化一张包含水果价格的表格.

width = input('Please enter width: ')

priceWidth = 10
itemWidth = width - priceWidth

headerFormat = '%-*s%*s'
format = '%-*s%*.2f'

print '=' * width

print headerFormat % (itemWidth,'Item',priceWidth,'Price')
print '-' * width

print format % (itemWidth,'Apples',priceWidth,0.4)
print format % (itemWidth,'Pears',priceWidth,0.5)
print format % (itemWidth,'Cantaloupes',priceWidth,1.92)
print format % (itemWidth,'Dried Apricots (16 oz.)',priceWidth,8)
print format % (itemWidth,'Prunes (4 lbs.)',priceWidth,12)

print '=' * width