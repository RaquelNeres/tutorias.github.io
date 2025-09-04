package poo2.pratica;

public class Conta {
    String numero;
    Pessoa titular;
    double saldo;
    double limite;

    double disponivel(){
        return this.saldo + this.limite; // this.nome se refere ao nome da class
    }

    void extrato(){
        System.out.println("*** EXTRATO DA CONTA ***");
        System.out.println("conta: " + this.numero);
        System.out.println("Titular: " + this.titular.cpf);
        System.out.println("Saldo disponivel para saque: " + this.disponivel());
    }

    void depoditar(double valor){
        this.saldo += valor;
    }

    boolean sacar(double valor){
        if (valor <= this.disponivel()){
            this.saldo -= valor;
            System.out.println("Saque na conta " + this.numero +
                    " realizado com sucesso. Novo saldo: " + this.saldo);
            return true;
        }

        else {
            System.out.println("ERRO: Saque na conta " + this.numero +
                    " nao foi realizado. Valor disponivel: " + this.disponivel());
            return false;
        }
    }
    boolean transferir(double valor, Conta destino){
        if (this.sacar(valor)){
            destino.depoditar(valor);
            System.out.println("Transferencia de " + valor + " da conta " +
                this.numero + " para a conta " + destino.numero + " foi realizado com sucesso.");
                return true;
        }
        else{
            System.out.println("ERRO: Nao foi possivel transferir " + 
            valor + " da conta " + this.numero + " para a conta " + 
            destino.numero +  " Valor disponivel: " + this.disponivel());
            return false;
        }
    }
    void chequeEspecial(double juro) {
        if (this.saldo < 0) {
            this.saldo = this.saldo * (1 + (juro / 100));
        }
    }
}