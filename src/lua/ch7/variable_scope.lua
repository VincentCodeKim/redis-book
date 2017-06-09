local function main(ARGV)
	local localval1 = 100
	local function myFunction()
		local localval1 = 10
	end

	return localval1
end


print(main())
