/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Caixeiro;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Josinaldo Batista
 */
public class Grafo extends JPanel {

    int[][] _cidades;
    int _centro;
    int _raio;
    
    /**
     * Inicializa os valores do centro, raio e matriz de cidades
     */
    public Grafo() {
        _cidades = new int[0][0];
        _centro = 165;
        _raio = 145;
    }
    
    /**
     * Inicializa os valores do centro, raio e matriz de cidades
     * @param cidades 
     */
    public void setCidades(int[][] cidades) {
        _cidades = cidades;
        _centro = 165;
        _raio = 145;
    }
    
    /**
     * Desenha a matriz de cidades na tela
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        Limpar(g);
        DesenharArestas(g);
        for (int i = 0; i < TotalCidades(); i++) {

            double cosseno = Math.cos(Math.toRadians(360 * i / TotalCidades()));
            double seno = Math.sin(Math.toRadians(360 * i / TotalCidades()));

            int x = _centro + (int) (_raio * cosseno);
            int y = _centro + (int) (_raio * seno);

            DesenharCidade(g, i, x, y);
        }
    }
    
    /**
     * Preenche toda a tela com o fundo branco
     * @param g 
     */
    private void Limpar(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, 500, 500);
    }
    
    /**
     * Desenha um vertice na tela
     * @param g
     * @param indice
     * @param x
     * @param y 
     */
    private void DesenharCidade(Graphics g, int indice, int x, int y) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(x, y, 20, 20);
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString(indice + "", x + ((indice < 10) ? 6 : 3), y + 15);
    }
    
    /**
     * Desenha todas as arestas na tela
     * @param g 
     */
    private void DesenharArestas(Graphics g) {
        for (int i = 0; i < TotalCidades(); i++) {
            for (int j = 0; j < TotalCidades(); j++) {
                if (_cidades[i][j] != 0) {
                    DesenharLinha(g, i, j);
                }
            }
        }
    }
    
    /**
     * Desenha uma arestas na tela
     * @param g
     * @param inicio
     * @param destino 
     */
    private void DesenharLinha(Graphics g, int inicio, int destino) {
        int xInicio, yInicio, xDestino, yDestino, valor;

        valor = _cidades[inicio][destino];

        double cossenoInicio = Math.cos(Math.toRadians(360 * inicio / TotalCidades()));
        double senoInicio = Math.sin(Math.toRadians(360 * inicio / TotalCidades()));
        
        double cossenoDestino = Math.cos(Math.toRadians(360 * destino / TotalCidades()));
        double senoDestino = Math.sin(Math.toRadians(360 * destino / TotalCidades()));

        xInicio = _centro + (int) (_raio * cossenoInicio);
        yInicio = _centro + (int) (_raio * senoInicio);

        xDestino = _centro + (int) (_raio * cossenoDestino);
        yDestino = _centro + (int) (_raio * senoDestino);

        g.setColor(Color.DARK_GRAY);
        g.drawLine(xInicio + 10, yInicio + 10, xDestino + 10, yDestino + 10);

        g.setColor(Color.CYAN);
        g.fillRect(xInicio - 2 + (int) ((xDestino - xInicio) * 0.25), yInicio - 12 + (int) ((yDestino - yInicio) * 0.25), 52, 17);

        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString(valor + ", " + inicio + "->" + destino, xInicio + (int) ((xDestino - xInicio) * 0.25), yInicio + (int) ((yDestino - yInicio) * 0.25));
    }
    
    /**
     * Retorna o total de cidades
     * @return 
     */
    private int TotalCidades() {
        if (_cidades == null || _cidades.length == 0) {
            return 0;
        } else {
            return _cidades[0].length;
        }
    }
}
