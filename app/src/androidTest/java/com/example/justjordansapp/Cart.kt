package com.example.justjordansapp

data class CartItem(
    val name: String,
    val price: Double,
    val quantity: Int
)

class CartAdapter(private val cartItems: List<CartItem>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val itemQuantity: TextView = itemView.findViewById(R.id.itemQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item_layout, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.itemName.text = item.name
        holder.itemPrice.text = "$${item.price}"
        holder.itemQuantity.text = "Qty: ${item.quantity}"
    }

    override fun getItemCount(): Int = cartItems.size
}

class MainActivity : AppCompatActivity() {

    private lateinit var cartAdapter: CartAdapter
    private val cartItems = mutableListOf<CartItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(cartItems)
        recyclerView.adapter = cartAdapter

        // Add dummy items to the cart
        cartItems.add(CartItem("Item 1", 10.0, 1))
        cartItems.add(CartItem("Item 2", 20.0, 2))
        cartAdapter.notifyDataSetChanged()

        // Set up image buttons for payments
        findViewById<ImageButton>(R.id.payWithCashApp).setOnClickListener {
            launchPayment("cashapp", 30.0) // Total cart value
        }

        findViewById<ImageButton>(R.id.payWithVenmo).setOnClickListener {
            launchPayment("venmo", 30.0)
        }

        findViewById<ImageButton>(R.id.payWithZelle).setOnClickListener {
            launchZellePayment(30.0)
        }
    }

    private fun launchPayment(app: String, amount: Double) {
        val uri: Uri = when (app) {
            "cashapp" -> Uri.parse("cashapp://pay?amount=$amount")
            "venmo" -> Uri.parse("venmo://paycharge?txn=pay&amount=$amount")
            else -> Uri.EMPTY
        }

        val intent = Intent(Intent.ACTION_VIEW, uri)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "$app not installed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun launchZellePayment(amount: Double) {
        Toast.makeText(this, "Zelle integration requires direct bank app redirection", Toast.LENGTH_SHORT).show()
        // Zelle doesn't have a public API; consider using payment links or invoices via SMS/email
    }
}
