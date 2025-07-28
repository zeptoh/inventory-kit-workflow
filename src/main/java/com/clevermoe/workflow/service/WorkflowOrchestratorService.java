package com.clevermoe.workflow.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clevermoe.workflow.dto.Customer;
import com.clevermoe.workflow.dto.InventoryManagement;
import com.clevermoe.workflow.dto.OrderSummary;
import com.clevermoe.workflow.dto.PricingEngine;
import com.clevermoe.workflow.dto.ProductCatalog;
import com.clevermoe.workflow.dto.Quotation;
import com.clevermoe.workflow.dto.ShippingOption;
import com.clevermoe.workflow.dto.TariffCustoms;
import com.clevermoe.workflow.dto.TaxCalculation;

@Service
public class WorkflowOrchestratorService {

    @Autowired
    private ProductCatalogService productCatalogService;

    @Autowired
    private PricingEngineService pricingEngineService;

    @Autowired
    private InventoryManagementService inventoryManagementService;

    @Autowired
    private TaxCalculationService taxCalculationService;

    @Autowired
    private TariffCustomsService tariffCustomsService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShippingService shippingService;

    @Autowired
    private QuotationService quotationService;

    @Autowired
    private OrderManagementService orderManagementService;

    public Object runWorkflow() {
        ProductCatalog[] productCatalogs = productCatalogService.execute();

        String productId = productCatalogs[0].getProductId();
        double productPrice = productCatalogs[0].getPrice();
        System.out.println(productId);

        JSONObject pricingJson = new JSONObject();
        pricingJson.put("productId", productId);
        PricingEngine pricingEngine = pricingEngineService.execute(pricingJson);

        JSONObject inventoryJson = new JSONObject();
        inventoryJson.put("productId", productId);
        InventoryManagement inventoryManagement = inventoryManagementService.execute(inventoryJson);

        JSONObject taxJson = new JSONObject();
        taxJson.put("productId", productId);
        TaxCalculation taxCalculation = taxCalculationService.execute(taxJson);

        JSONObject tariffJson = new JSONObject();
        tariffJson.put("productId", productId);
        TariffCustoms tariffCustoms = tariffCustomsService.execute(tariffJson);

        JSONObject customerJson = new JSONObject();
        customerJson.put("email", "customer1@example.com");
        Customer customer = customerService.execute(customerJson);

        JSONObject shippingJson = new JSONObject();
        shippingJson.put("isLoyal", customer.getIsLoyal());
        shippingJson.put("zone", customer.getAddressZone());
        ShippingOption[] shippingOption = shippingService.execute(shippingJson);

        JSONObject quotationJson = new JSONObject();
        quotationJson.put("customerId", customer.getId());
        JSONObject itemJson = new JSONObject();
        itemJson.put("productId", productId);
        itemJson.put("quantity", 5);
        itemJson.put("unitprice", productPrice);
        itemJson.put("taxAmount", taxCalculation.getTaxAmount());
        itemJson.put("dutyRate", tariffCustoms.getDutyRate());
        itemJson.put("discountAmount", pricingEngine.getDiscount());
        JSONArray itemJsonArray = new JSONArray();
        itemJsonArray.put(itemJson);
        quotationJson.put("items", itemJsonArray);
        quotationJson.put("deliveryCharge", shippingOption[0].getCost());
        quotationJson.put("estimatedDeliveryDays", shippingOption[0].getEstimatedDeliveryDays());
        Quotation quotation = quotationService.execute(quotationJson);

        JSONObject orderJson = new JSONObject();
        orderJson.put("productId", productId);
        orderJson.put("pricingId", pricingEngine.getId());
        orderJson.put("inventoryId", inventoryManagement.getId());
        orderJson.put("taxId", taxCalculation.getId());
        orderJson.put("tarrifId", tariffCustoms.getId());
        orderJson.put("customerId", customer.getId());
        orderJson.put("shippingId", shippingOption[0].getId());
        orderJson.put("quotationId", quotation.getId());
        OrderSummary orderSummary = orderManagementService.execute(orderJson);

        return orderSummary;
    }
}
