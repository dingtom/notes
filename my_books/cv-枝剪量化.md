---
    title: "cv-枝剪量化"
    categories: 
        - cv
    date: 2022-12-08T09:49:29+08:00
---

**MIPS**：Million Instructions executed Per Second,每秒执行百万条指令，用来计算同一秒内系统的处理能力，即每秒执行了多少百万条指令。
**DMIPS**：Dhrystone Million Instructions executed Per Second,主要用于测试整数计算能力。
**MFLOPS**：Million Floating-point Operations per Second主要用于测浮点计算能力。



- 模型压缩

知识蒸馏(teacher-student网络)

light weight结构设计，例如MobileNet、 SqueezeNet

模型量化(二值化网络，INT8量化)

- 模型精简

CNN剪枝(结构化剪枝、非结构化剪枝)
低秩分解

- 加速计算

Op-level快速算法
Layer-level快速算法FFT Conv2d等

- 优化加速

NAS（神经网络架构搜索）
网络并行结构合并(tensorRT)

