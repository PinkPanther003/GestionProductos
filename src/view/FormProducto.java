/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import DAO.ProductoDAO;
import model.Producto;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Formulario único de mantenimiento de productos (ABM + Consulta)
 */
public class FormProducto extends JFrame {

    private final ProductoDAO dao = new ProductoDAO();
    private DefaultTableModel modeloTabla;

    // Componentes de la interfaz
    private JTextField txtId, txtCodigo, txtNombre, txtCategoria, txtPrecio, txtStock, txtBuscar;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar, btnConsultar;
    private JTable tablaProductos;

    public FormProducto() {
        initComponents();
        this.setLocationRelativeTo(null);
        txtId.setEditable(false);
        listarProductos();
    }

    private void initComponents() {
        setTitle("Gestión de Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 550);

        // --- PANEL SUPERIOR DE BÚSQUEDA (CONSULTAR) ---
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Consultar Producto (GET)"));
        
        panelBusqueda.add(new JLabel("Código o ID:"));
        txtBuscar = new JTextField(15);
        panelBusqueda.add(txtBuscar);

        btnConsultar = new JButton("Consultar");
        panelBusqueda.add(btnConsultar);
        
        btnConsultar.addActionListener(e -> consultarProducto());

        // --- PANEL IZQUIERDO (CAMPOS Y BOTONES) ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(15);
        txtCodigo = new JTextField(15);
        txtNombre = new JTextField(15);
        txtCategoria = new JTextField(15);
        txtPrecio = new JTextField(15);
        txtStock = new JTextField(15);

        // Fila 0: ID
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; panelForm.add(txtId, gbc);

        // Fila 1: Código
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1; panelForm.add(txtCodigo, gbc);

        // Fila 2: Nombre
        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; panelForm.add(txtNombre, gbc);

        // Fila 3: Categoría
        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(new JLabel("Categoría:"), gbc);
        gbc.gridx = 1; panelForm.add(txtCategoria, gbc);

        // Fila 4: Precio
        gbc.gridx = 0; gbc.gridy = 4; panelForm.add(new JLabel("Precio:"), gbc);
        gbc.gridx = 1; panelForm.add(txtPrecio, gbc);

        // Fila 5: Stock
        gbc.gridx = 0; gbc.gridy = 5; panelForm.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1; panelForm.add(txtStock, gbc);

        // Panel de Botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 5, 5));
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnLimpiar = new JButton("Limpiar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnEliminar);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        panelForm.add(panelBotones, gbc);

        // Eventos
        btnGuardar.addActionListener(e -> guardarProducto());
        btnActualizar.addActionListener(e -> actualizarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        // --- PANEL DERECHO (TABLA DE DATOS) ---
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Código", "Nombre", "Categoría", "Precio", "Stock"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seleccionarFila();
            }
        });
        JScrollPane scrollTabla = new JScrollPane(tablaProductos);

        // Layout Principal
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().add(panelBusqueda, BorderLayout.NORTH);
        getContentPane().add(panelForm, BorderLayout.WEST);
        getContentPane().add(scrollTabla, BorderLayout.CENTER);
    }

    /**
     * MÉTODO CONSULTAR (GET)
     * Busca un Producto por su código o ID y retorna un objeto de tipo Producto.
     */
    private void consultarProducto() {
        String criterio = txtBuscar.getText().trim();
        if (criterio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código o ID para consultar.");
            return;
        }

        // Llamada al método GET del DAO que retorna el Objeto
        Producto p = dao.obtenerPorCodigoOId(criterio);

        if (p != null) {
            // Se llena el formulario con las propiedades del Objeto recibido
            txtId.setText(String.valueOf(p.getId()));
            txtCodigo.setText(p.getCodigo());
            txtNombre.setText(p.getNombre());
            txtCategoria.setText(p.getCategoria());
            txtPrecio.setText(String.valueOf(p.getPrecio()));
            txtStock.setText(String.valueOf(p.getStock()));
            JOptionPane.showMessageDialog(this, "¡Producto encontrado!");
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró ningún producto con el criterio ingresado.", "Sin Resultados", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void listarProductos() {
        modeloTabla.setRowCount(0);
        List<Producto> lista = dao.listar();
        if (lista != null) {
            for (Producto p : lista) {
                modeloTabla.addRow(new Object[]{p.getId(), p.getCodigo(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()});
            }
        }
    }

    private void guardarProducto() {
        if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty() || txtPrecio.getText().isEmpty() || txtStock.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete los campos obligatorios.");
            return;
        }

        try {
            Producto p = new Producto(
                txtCodigo.getText(),
                txtNombre.getText(),
                txtCategoria.getText(),
                Double.parseDouble(txtPrecio.getText()),
                Integer.parseInt(txtStock.getText())
            );
            if (dao.registrar(p)) {
                JOptionPane.showMessageDialog(this, "Producto guardado con éxito.");
                listarProductos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el producto.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio y Stock deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarProducto() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione o consulte un producto para actualizar.");
            return;
        }
        try {
            Producto p = new Producto(
                Integer.parseInt(txtId.getText()),
                txtCodigo.getText(),
                txtNombre.getText(),
                txtCategoria.getText(),
                Double.parseDouble(txtPrecio.getText()),
                Integer.parseInt(txtStock.getText()),
                "Activo"
            );
            if (dao.actualizar(p)) {
                JOptionPane.showMessageDialog(this, "Producto actualizado con éxito.");
                listarProductos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el producto.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminarProducto() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione o consulte un producto para eliminar.");
            return;
        }
        int id = Integer.parseInt(txtId.getText());
        if (JOptionPane.showConfirmDialog(this, "¿Desea eliminar este producto?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (dao.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Producto eliminado.");
                listarProductos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar.");
            }
        }
    }

    private void seleccionarFila() {
        int fila = tablaProductos.getSelectedRow();
        if (fila >= 0) {
            txtId.setText(tablaProductos.getValueAt(fila, 0).toString());
            txtCodigo.setText(tablaProductos.getValueAt(fila, 1).toString());
            txtNombre.setText(tablaProductos.getValueAt(fila, 2).toString());
            txtCategoria.setText(tablaProductos.getValueAt(fila, 3).toString());
            txtPrecio.setText(tablaProductos.getValueAt(fila, 4).toString());
            txtStock.setText(tablaProductos.getValueAt(fila, 5).toString());
        }
    }

    private void limpiarCampos() {
        txtBuscar.setText("");
        txtId.setText("");
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCategoria.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new FormProducto().setVisible(true));
    }
}