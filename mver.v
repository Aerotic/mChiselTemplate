module mver(
    input reg [2:0] ver_in;
    input wire ver_clk
    output reg [7:0] ver_out;
);
always @(posedge ver_clk ) begin
    case (ver_in)
        3'b000: ver_out = 8'b0000_0001;
        3'b001: ver_out = 8'b0000_0010;
        3'b010: ver_out = 8'b0000_0100;
        3'b011: ver_out = 8'b0000_1000;
        3'b100: ver_out = 8'b0001_0000;
        3'b101: ver_out = 8'b0010_0000;
        3'b110: ver_out = 8'b0100_0000;
        3'b111: ver_out = 8'b1000_0000;        
        default:  ver_out = 8'b0000_0001;
    end
end
endmodule // mver