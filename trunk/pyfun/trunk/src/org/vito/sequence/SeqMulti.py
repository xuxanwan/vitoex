#!/usr/bin/python
# coding=utf-8
'''
Created on 2010-8-26
@author: vito
'''
#序列乘法示例
#以正确的宽度在居中的"盒子"内打印一个句子.

sentence = raw_input("Pls input a sentence: ")

screenWidth = 80
textWidth = len(sentence)
boxWidth = textWidth + 6  #设置显示"盒子"的宽度
leftMargin = (screenWidth - boxWidth) // 2  #//,取整的除法

print
print ' ' * leftMargin + '+' + '-' * (boxWidth - 2) + '+'
print ' ' * leftMargin + '|' + ' ' * (boxWidth - 2) + '|'
print ' ' * leftMargin + '|  ' + sentence + '  |'
print ' ' * leftMargin + '|' + ' ' * (boxWidth - 2) + '|'
print ' ' * leftMargin + '+' + '-' * (boxWidth - 2) + '+'