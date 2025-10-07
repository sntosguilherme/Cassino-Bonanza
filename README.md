# 🎰 Cassino Bonanza

## 1. INTRODUÇÃO

O projeto **Cassino Bonanza** é um jogo que reúne alguns jogos presentes em cassino, mas **sem o intuito de apostar dinheiro**. A diversão do jogo está na **competitividade entre jogadores**, garantida por um **ranking global persistente**. O Cassino Bonanza está dividido em 2 mini jogos: uma **Roleta** e um jogo de **Slot**. Em cada um deles o jogador poderá “apostar” uma quantidade de pontos, que serão multiplicados ou perdidos de acordo com os resultados dos mini jogos.

No jogo, o objetivo do jogador é fazer a maior pontuação possível e escolher o momento correto de sair, para assim obter a melhor colocação possível no ranking. Cassino Bonanza foi desenvolvido em **Java**, utilizando as bibliotecas padrão e a biblioteca **Gson** para manipulação do arquivo Json onde está presente o ranking. A interface do jogo foi feita em **Java Swing**.

---

## 2. CASSINO BONANZA

O jogo abre com a classe `menu`, que possui 3 botões: o botão de sair, o botão que mostra o ranking e o botão de iniciar um jogo. O botão de iniciar um jogo abre o **hub de jogos**, onde estão disponíveis botões que levam a todos os minijogos e um botão de encerrar a jogatina, além do saldo do jogador.

O **ranking** do jogo é armazenado em um arquivo **Json**, onde todo jogador que encerra a sua jogatina no hub consegue salvar seu progresso. Se o jogador estiver entre os **10 maiores pontuadores**, a sua pontuação será exibida no ranking do menu.

### 2.1 Roleta

Esse mini jogo é baseado na **Roleta Europeia**, que possui números de 0 a 36 distribuídos ao longo de uma roleta, coloridos num fundo preto ou vermelho - com exceção do 0, que possui fundo verde. A escolha do jogador é simples, ele deve apostar uma quantia em qualquer uma das cores disponíveis, para que então a roleta seja girada no sentido anti-horário, enquanto uma bola é jogada no sentido horário em cima das casas numeradas.

Se a predição seja correta, o jogador recebe o valor apostado acrescido de multiplicador de ganho, como na **Tabela 1**.

**Tabela 1 - Possíveis ganhos e suas probabilidades**

| Opção de Aposta | Possíveis Ganhos | Probabilidade (%) |
| :-------------- | :--------------- | :---------------- |
| Verde           | 35:1             | 2.70%             |
| Vermelho        | 1:1              | 48.65%            |
| Preto           | 1:1              | 48.65%            |

*Fonte: Elaborada pelo autor.*

O jogador ganha o equivalente a sua aposta se acertar vermelho ou preto, ou 35 vezes o valor apostado se acertar o verde (número 0), com o saldo sendo atualizado dinamicamente conforme as apostas são realizadas e os resultados são computados. Nas apostas de vermelho/preto a probabilidade real seria de 50%, mas a presença do **0** reduz para **48.65%**, criando uma pequena vantagem da casa. Contudo, a chance de ganhos expressivos no verde e a liberdade de controlar o valor das apostas conferem maior equilíbrio e emoção ao jogo.

### 2.2 Slot

O mini jogo de Slot consiste em uma máquina caça-níquel com **3 espaços** que serão preenchidos por 1 dos 4 símbolos presentes no jogo: **Barra, Cereja, Moeda e Sete**. O jogador ganha quando há um *Two-of-a-Kind* (dois símbolos iguais em sequência) ou um *Three-of-a-Kind* (três símbolos iguais em sequência).

**Tabela 2 - Condições de vitória, multiplicadores de ganho e suas probabilidades**

| Categoria | Símbolo (Valor) | Condição de Vitória | Multiplicador de Ganho | Probabilidade (%) |
| :--- | :--- | :--- | :--- | :--- |
| **Símbolo Individual** | Barra (1) | - | - | 40.00% |
| | Cereja (2) | - | - | 25.00% |
| | Moeda (3) | - | - | 20.00% |
| | Sete (4) | - | - | 15.00% |
| **Three-of-a-Kind** | 3x Barra | S1=S2=S3 | 2× | 6.40% |
| | 3x Cereja | S1=S2=S3 | 8× | 1.56% |
| | 3x Moeda | S1=S2=S3 | 16× | 0.80% |
| | 3x Sete | S1=S2=S3 | 40× | 0.34% |
| **Two-of-a-Kind** | 2x Barra | S1=S2 e S1 != S3 ou S2=S3 e S2 != S1 | 1.2× | 19.20% |
| | 2x Cereja | S1=S2 e S1 != S3 ou S2=S3 e S2 != S1 | 2.4× | 9.38% |
| | 2x Moeda | S1=S2 e S1 != S3 ou S2=S3 e S2 != S1 | 4.8× | 6.40% |
| | 2x Sete | S1=S2 e S1 != S3 ou S2=S3 e S2 != S1 | 10× | 3.83% |

*Fonte: Elaborada pelo autor.*

Como é possível ver na tabela acima, a probabilidade dos símbolos ocuparem um dos espaços são diferentes, portanto combinações envolvendo diferentes símbolos geram premiações diferentes. A premiação máxima (**Jackpot**) é o *Three-of-a-Kind* do símbolo **Sete**, que paga **100 vezes** a aposta realizada pelo jogador.

**Tabela 3 - Probabilidade de vitória**

| Tipo | Pagamento | Probabilidade |
| :--- | :--- | :--- |
| Qualquer Three-of-a-Kind | 2× a 40× | 9.10% |
| Qualquer Two-of-a-Kind (e não três) | 1.2× a 10× | 38.81% |
| **Probabilidade Total de Ganho** | - | **47.91%** |

*Fonte: Elaborada pelo autor.*

A **Tabela 3** mostra que as chances do jogador receber algum pagamento em pontos é de **47.91%**. Portanto, a longo prazo o jogador tende a perder, mas as chances são equilibradas o suficiente para que o jogador consiga obter algum lucro significativo durante sua jogatina.

---

## 3. METODOLOGIA

Para a elaboração do código em Java, foram utilizadas as IDEs **Netbeans** e **Intellij**. Já para organização das ideias e monitoramento do progresso foi utilizado o aplicativo de produtividade **Notion**. O **google planilhas** foi usado na elaboração das tabelas relativas às probabilidades envolvidas no jogo.

No mais, para elaboração colaborativa do projeto, foi utilizado o **Git** e o **GitHub**, e cada membro do trabalho ficou responsável pela maior parte da elaboração de um mini jogo. As demais classes que envolvem o projeto foram feitas de maneira colaborativa.

Quanto às bibliotecas java utilizadas no projeto, as **bibliotecas padrão do java** foram as mais utilizadas, e a única biblioteca adicionada como um arquivo JAR externo foi a biblioteca **Gson**, que permite a conversão de objetos em Java para arquivos Json e vice-versa. A biblioteca **Java Swing** foi empregada na elaboração das Interfaces gráficas de usuário presentes no projeto.

---

## 4. CONSIDERAÇÕES FINAIS

Através do projeto Cassino Bonanza, foi possível aprofundar os conhecimentos obtidos durante a disciplina de **POO**, empregando conceitos como herança, polimorfismo, encapsulamento, abstração, composição, classes abstratas, interfaces e coleções de dados como listas e arrays.

O jogo representa uma maneira divertida de testar sua sorte e desafiar seus amigos, e pode ser facilmente expandido por meio de novas classes que representam novos jogos. Entretanto, a biblioteca **Java Swing**, por ser uma biblioteca antiga, não é a melhor alternativa atual para lidar com interfaces que envolvem animação, como as dos dois mini jogos presentes no Cassino Bonanza. Para isso, bibliotecas como a **JavaFX** seriam mais apropriadas.

Além disso, na lógica do ranking persistente presente no código, cada vez que um jogador é adicionado ao ranking, ou a interface gráfica do ranking funciona, todo o arquivo json precisa ser lido. Essa é uma maneira **ineficiente** de lidar com os dados, que pode gerar lentidão, caso o arquivo venha a ter grandes dimensões.

---
