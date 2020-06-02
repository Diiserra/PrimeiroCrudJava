
package dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Produto;

public class ProdutoDAO {
 
    Connection con;
    PreparedStatement st;
    ResultSet rs;
    
    //Metodo responsavel por inserir novos dados ao banco
    public boolean create(Produto produto){
        String sql = "INSERT INTO produto VALUES(?,?,?,?)";//Variavel contendo o comando sql
        con = ConnectionFactory.getConnection();//Conexao com o banco iniciada
        try {
            st = con.prepareStatement(sql);//Iniciando uma sessão no banco de dados passando o comando como parametro
            st.setInt(1, produto.getCodigo());//Configurando o numero do campo e o valor inserido dentro do campo escolhido
            st.setString(2, produto.getNome());
            st.setInt(3, produto.getQuantidade());
            st.setInt(4, produto.getPreco());
            st.executeUpdate();//Executando o comando update que atualiza o banco jutantos todos as configuraçoes feita acima
            return true;//Retorna true caso conexao seja estabelecida
        }catch (SQLException ex) {
            System.err.println("Error ao tentar salvar"+ ex);
            return false;//Retorna false caso conexao nao seja estabelecida
        }finally{
            ConnectionFactory.closeConnection(con, st);//Fecha a conexao ao banco
        }
    }
    
    public List<Produto> read(){
        List<Produto> produtos = new ArrayList<>();//Criando o obj produtos do tipo List<Produto>
        String sql = "SELECT * FROM produto";//Variavel com o comando sql dentro
        con = ConnectionFactory.getConnection();//Conexao com o banco iniciada
        try {
            st = con.prepareStatement(sql);//Iniciando uma sessão no banco de dados passando o comando como parametro
            rs = st.executeQuery();//O comando executeQuery retorna um tipo ResultSet, por isso tem que ter uma variavel tipo ResultSet pra guardar o retorno do executeQuery()
            
            while (rs.next()){//O metodo next() vai fazer a funçao de percorer toda a lista do banco, enquanto tirar itens na lista ele vai ser true
                Produto produto = new Produto();//Instanciando o obj produto
                produto.setCodigo(rs.getInt("codigo"));
                produto.setNome(rs.getString("nome"));//Settando os atributos do obj produto
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setPreco(rs.getInt("preco"));
                produtos.add(produto);//adicionando o obj funcionario no ArrayList tipo Produto
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao ler o banco "+ex);
        }finally{
            ConnectionFactory.closeConnection(con, st, rs);//fechando as conexoes com o banco
        }
        return produtos;//retornando a lista de Produto
    }
    
    public void updade(Produto produto){
        String sql = "UPDATE produto SET  nome = ?, quantidade = ?, preco = ? WHERE codigo = ?";
        con = ConnectionFactory.getConnection();
        try {
            st = con.prepareStatement(sql);
            st.setString(1, produto.getNome());
            st.setInt(2, produto.getQuantidade());
            st.setInt(3, produto.getPreco());
            st.setInt(4, produto.getCodigo());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro ao tentar atualizar "+ex);
        }finally{
            ConnectionFactory.closeConnection(con, st);
        }
    }
    
    public void delete(Produto produto){
        String sql = "DELETE FROM produto WHERE codigo = ?";//Variavel com o comando sql
        con = ConnectionFactory.getConnection();//Abrindo conexao com o banco de dados
        try {
            st = con.prepareStatement(sql);//Inciando a sessao no banco passando o comando sql
            st.setInt(1, produto.getCodigo());//Configurando o que vai ser passado na "?"
            st.executeUpdate();//Executando o comando sql completo
        } catch (SQLException ex) {
            System.err.println("Erro ao tentar deletar"+ ex);//Mensagem se der erro ao deletar
        }finally{
            ConnectionFactory.closeConnection(con, st);//Fechando a conexao
        }
    }
}
