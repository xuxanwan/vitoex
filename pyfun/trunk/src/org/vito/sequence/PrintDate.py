#!/usr/bin/python
# coding=utf-8
'''
Created on 2010-8-25
@author: vito
'''
#根据给定的年月日数字,以给定形式打印日期

months = ['January','February','March',
          'April','May','June',
          'July','August','September',
          'October','November','December']

#以1~31的数字作为结尾的列表
ending = ['st','nd','rd'] + 17 * ['th'] + \
    ['st','nd','rd'] + 7 * ['th'] + ['st']

year = raw_input('Year: ')
month = raw_input('Month(1~12): ')
day = raw_input('Day(1~31): ')

month_number = int(month)
day_number = int(day)

month_name = months[month_number - 1]
ordinal = day + ending[day_number - 1]

print year + ', ' + month_name + ' ' + ordinal
