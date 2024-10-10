# Projeto: Supermercado Concorrente

## Descrição do Projeto

Este projeto apresenta uma simulação de um supermercado com múltiplos caixas e clientes, em um ambiente de concorrência, onde a exclusão mútua entre os processos (caixas) deve ser garantida. O objetivo principal é gerenciar a concorrência entre os caixas para que apenas um cliente seja atendido por vez em cada caixa, utilizando mecanismos de coordenação como mutex e semáforos.

**Vá a branch master para acessar o código.**

Além de assegurar o funcionamento correto da aplicação, o projeto também inclui a possibilidade de provocar e demonstrar problemas de concorrência, como:

- **Violação da Exclusão Mútua:** Cenário onde a exclusão mútua é violada e mais de um processo tenta acessar o mesmo recurso simultaneamente.
- **Deadlock:** Situação onde dois ou mais processos ficam bloqueados esperando um ao outro liberar um recurso, levando à paralisação do sistema.
- **Inanição (Starvation):** Quando um processo nunca consegue acessar o recurso porque outros processos continuam monopolizando o acesso.

A aplicação foi desenvolvida em **Java**, utilizando interfaces gráficas (Swing) para configurar a simulação.

## Funcionalidades

- **Simulação de Supermercado:** Múltiplos caixas atendendo clientes, com controle de fila e tempo de atendimento.
- **Configuração Personalizada:** Permite ajustar o número de caixas, clientes, tempo de funcionamento, e a probabilidade de um cliente precisar de troco.
- **Simulação de Problemas de Concorrência:**
  - Deadlock: Simulação de um cenário onde caixas bloqueiam uns aos outros ao precisar de troco.
  - Inanição: Simulação de um cenário onde alguns clientes não são atendidos, resultando em inanição.
  - Violação da Exclusão Mútua: Simulação onde a exclusão mútua é violada intencionalmente.
  
## Como Funciona

1. **Caixas e Clientes:** A aplicação cria múltiplos caixas e clientes, distribuindo os clientes entre os caixas de acordo com as configurações definidas.
2. **Concorrência:** Cada caixa funciona em uma thread separada, e o sistema garante que apenas um cliente por vez seja atendido por cada caixa.
3. **Problemas de Concorrência:** O usuário pode optar por simular cenários de deadlock, inanição, ou violação da exclusão mútua.

## Mecanismos de Coordenação Utilizados

- **Mutex e Semáforos:** Utilizados para garantir a exclusão mútua no acesso aos caixas.
- **Monitor:** Para sincronizar o acesso dos clientes aos caixas.
