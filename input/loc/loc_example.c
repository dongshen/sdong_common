/*
 * Copyright (C), 1988-1999, Xxxxxx Tech. Co., Ltd.
 * FileName: xxx
 * Description: balabalabalabalabalabalabalabalabala
    balabalabalabalabalabalabalabalabalabalabalabala
 * Change Logs: 
    |Date           |Author       |Notes     |version
    |2010-03-22     |XXX          |XXX       |1.0.0
 */

/*
 * Function:
 * Description:
 * Calls:
 * Input:
 * Input:
 * Output:
 * Return:
 * Others:
 */

/* @define xxx */
#define XXX_ERROR_OK              0   /* No error           */
#define XXX_ERROR_INVALID_TOKEN   1   /* Invalid token      */
#define XXX_ERROR_EXPECT_TYPE     2   /* Expect a type      */

/* @struct xxx */
struct xxx_syscall_item
{
    struct xxx_syscall_item* next;    /* 下一个item */
    struct xxx_syscall syscall;       /* syscall */
};

/* @variable temp */
/* Scope: 存储温度值 */
/* Range: -128 - 127 */
/* Notice: xxxxx */
/* Others: xxxxx */
extern char temp = 0;

/*************************************************
Copyright:East
Author:Jason
Date:2017-03-02
Description:描述主要实现的功能
**************************************************/

/* sccp interface with sccp user primitive message name */
enum SCCP_USER_PRIMITIVE
{
    N_UNITDATA_IND, /* sccp notify sccp user unit data come */
    N_NOTICE_IND,   /* sccp notify user the No.7 network can not */
                    /* transmission this message */
    N_UNITDATA_REQ, /* sccp user's unit data transmission request*/
};
 
/* The ErrorCode when SCCP translate */
/* Global Title failure, as follows */      // 变量作用、含义

/* Global Title failure, as follows */      /* 变量作用、含义
* more line 
*/

void example_fun( void )
{

    /* code one comments */
    CodeBlock One

    printf("Comments in C begin with /* or //.\n" );

     /* code two comments (40,7) */
    CodeBlock Two

    /* 暂时注释掉这两行：
    const double pi = 3.1415926536;  // pi是一个常量

    area = pi * r * r;   // 计算面积
    暂时注释到此 */
}


    int open( const char *name, int mode, … /* int permissions */ );

#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <io.h>
 
 
/**
 * 功能：移除C/C++程序代码中的注释
 *
 * 输入：指向C/C++程序代码的指针
 */
 
void
remove_comment(char *buf, size_t size)
{
  char *p, *end, c;
  char *sq_start, *dq_start;
  char *lc_start, *bc_start;
  size_t len;
 
  p = buf;
  end = p + size;
 
  sq_start = NULL;
  dq_start = NULL;
 
  lc_start = NULL;
  bc_start = NULL;
 
  while (p < end) {
 
  c = *p;
 
  switch (c) {
 
  case '\'': /* 单引号 */
  if (dq_start || lc_start || bc_start) {
  /* 忽略字符串与注释中的单引号 */
  p++;
  continue;
  }
 
  if (sq_start == NULL) {
  sq_start = p++;
 
  } else {
  len = p++ - sq_start;
 
  if (len == 2 && *(sq_start + 1) == '\\') {
  /* 忽略字符中的单引号 */
  continue;
  }
 
  sq_start = NULL;
  }
 
  break;
 
  case '\"': /* 双引号 */
  if (sq_start || lc_start || bc_start) {
  /* 忽略字符或注释中的双引号 */
  p++;
  continue;
  }
 
  if (dq_start == NULL) {
  dq_start = p++;
 
  } else {
  if (*(p++ - 1) == '\\') {
  /* 忽略字符串中的双引号 */
  continue;
  }
 
  dq_start = NULL;
  }
 
  break;
 
  case '/': /* 斜杆 */
  if (sq_start || dq_start || lc_start || bc_start) {
  /* 忽略字符、字符串或注释中的斜杆 */
  p++;
  continue;
  }
 
  c = *(p + 1);
 
  if (c == '/') {
  lc_start = p;
  p += 2;
 
  } else if (c == '*') {
  bc_start = p;
  p += 2;
 
  } else {
  /* 忽略除号 */
  p++;
  }
 
  break;
 
  case '*': /* 星号 */
  if (sq_start || dq_start || lc_start || bc_start == NULL) {
  /* 忽略字符、字符串或行注释中的星号，还有忽略乘号 */
  p++;
  continue;
  }
 
  if (*(p + 1) != '/') {
  /* 忽略块注释中的星号 */
  p++;
  continue;
  }
 
  p += 2;
 
  memset(bc_start, ' ', p - bc_start);
 
  bc_start = NULL;
 
  break;
 
  case '\n': /* 换行符 */
  if (lc_start == NULL) {
  p++;
  continue;
  }
 
  c = *(p - 1);
 
  memset(lc_start, ' ',
  (c == '\r' ? (p++ - 1) : p++) - lc_start);
 
  lc_start = NULL;
 
  break;
 
  default:
  p++;
  break;
  }
  }
 
  if (lc_start) {
  memset(lc_start, ' ', p - lc_start);
  }
}
 
 
int
main(int argc, char *argv[])
{
  int fd, n;
  char buf[102400];
 
  fd = open("C:\\Documents and Settings\\Administrator\\桌面\\remove_comment.c",
  _O_RDONLY, 0);
  if (fd == -1) {
  return -1;
  }
 
  n = read(fd, buf, sizeof(buf));
  if (n == -1 || n == 0) {
  close(fd);
  return -1;
  }
 
  //printf("test\n");
 
  remove_comment(buf, n);
 
  *(buf + n) = '\0';
 
  printf(buf);
  /***********\\\/////// */
 
  close(fd);
 
  return 0;
}